package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.UnknownHostException;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String IP_FORWARDED_FOR = "X-Forwarded-For";

    // path prefixes that will be protected by the authentication filter
//    public Set<String> SECURED_PATHS = Set.of("/offers", "/bids", "/accounts");
    public Set<String> SECURED_PATHS = Set.of("/dummy");
    // A variable reboot signature can be used as an additional security layer in
    // authentication tokens.
    private static final double REBOOT_CODE = 63.0427; // Math.random().

    // JWT configuration that can be adjusted from application.properties
    @Value("${jwt.issuer:private company}")
    private String issuer;

    @Value("${jwt.passphrase:This is very secret information for my private encryption key.}")
    private String passphrase;

    @Value("${jwt.duration-of-validity:1200}") // Default 20 minutes.
    private int tokenDurationOfValidity;

    /**
     * Enable CORS for all origins
     * 
     * @param registry CORS registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // all endpoints
                .allowedOriginPatterns("http://localhost:*", getHostIPAddressPattern(), "http://*.hva.nl:*") // all
                                                                                                             // origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // all methods
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .allowCredentials(true);
    }

    /**
     * Get the host IP address pattern
     * 
     * @return host IP address pattern
     */
    private String getHostIPAddressPattern() {
        try {
            return "http://" + java.net.InetAddress.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException e) {
            return "";
        }
    }

    public String getIssuer() {
        // include a reboot sequence nr in the issuer signature
        // such that authentication tokens can be revoked after a reboot.
        return String.format("%s-%f", this.issuer, REBOOT_CODE);
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public void setTokenDurationOfValidity(int tokenDurationOfValidity) {
        this.tokenDurationOfValidity = tokenDurationOfValidity;
    }

}
