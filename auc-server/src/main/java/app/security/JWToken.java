package app.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import app.WebConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Represents a JSON Web Token (JWT) used for authentication and authorization.
 */
public class JWToken {

    private static final String JWT_ISSUER_CLAIM = "iss";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_ACCOUNTID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";
    public static final String JWT_ATTRIBUTE_NAME = "JWTokenInfo";

    private String callName = null;
    private Long accountId = null;
    private String role = null;
    private String ipAddress;

    public JWToken(String callName, Long accountId, String role, String sourceIpAddress) {
        this(callName, accountId, role);
        this.setIpAddress(sourceIpAddress);
    }

    public JWToken(String callName, Long accountId, String role) {
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    private static Key getKey(String passphrase) {
        return new SecretKeySpec(passphrase.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * Encodes the JWToken with the specified issuer, passphrase, and expiration
     * time.
     *
     * @param issuer     the issuer of the token
     * @param passphrase the passphrase used to generate the key
     * @param expiration the expiration time of the token in seconds
     * @return the encoded JWToken as a string
     */
    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, this.callName)
                .claim(JWT_ACCOUNTID_CLAIM, this.accountId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Decodes the specified JWToken with the specified passphrase.
     * 
     * @param token      the JWToken to decode
     * @param issuer     the issuer of the token
     * @param passphrase the passphrase used to generate the key
     * @return the decoded JWToken
     * @throws ExpiredJwtException   if the token is expired
     * @throws MalformedJwtException if the token is malformed
     */
    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        Key key = getKey(passphrase);

        // Parse the token and extract the claims.
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        // Check if the token is expired.
        if (!claims.get(JWT_ISSUER_CLAIM).toString().equals(issuer)) {
            throw new MalformedJwtException("Invalid issuer");
        }

        // Create a JWToken object from the claims.
        JWToken jwToken = new JWToken(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_ACCOUNTID_CLAIM).toString()),
                claims.get(JWT_ROLE_CLAIM).toString());

        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS_CLAIM));

        return jwToken;
    }

    /**
     * Validates the JWToken with the specified issuer and passphrase.
     * 
     * @param token      the JWToken to validate
     * @param issuer     the issuer of the token
     * @param passphrase the passphrase used to generate the key
     * @return true if the token is valid, false otherwise
     */
    public long validateImpersonation(long targetAccountId) {
        if (targetAccountId == 0)
            return this.getAccountId();
        else if (targetAccountId == this.getAccountId() || this.isAdmin())
            return targetAccountId;
        else
            return -1L;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.toLowerCase().contains("admin");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Gets the IP address of the specified request.
     * 
     * @param request the request to get the IP address from
     * @return the IP address of the specified request
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        if (WebConfig.IP_FORWARDED_FOR != null) {
            ipAddress = request.getHeader(WebConfig.IP_FORWARDED_FOR);
        }

        // If the IP-Forwarded-For header is not present, use the remote address.
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

}
