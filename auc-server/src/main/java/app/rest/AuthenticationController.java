package app.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.WebConfig;
import app.exceptions.NotAcceptableException;
import app.models.Account;
import app.models.ViewClasses;
import app.repositories.AccountsRepositoryJpa;
import app.security.JWToken;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    WebConfig webConfig;

    @Autowired
    AccountsRepositoryJpa accountsRepo;

    @JsonView(ViewClasses.Summary.class)
    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody ObjectNode loginInfo,
            HttpServletRequest request) throws NotAcceptableException {

        String email = loginInfo.get("email").asText();
        String password = loginInfo.get("password").asText();

        // Check whether we need and have the source IP-address of the user.
        String ipAddress = JWToken.getIpAddress(request);
        if (ipAddress == null) {
            throw new NotAcceptableException("Cannot identify your source IP-Address.");
        }

        // Check whether the user exists and the password is correct.
        List<Account> accounts = this.accountsRepo.findByEmail(email);
        Account account = accounts.isEmpty() ? null : accounts.get(0);

        if (account == null || !account.verifyPassword(password)) {
            throw new NotAcceptableException("Invalid email or password.");
        }

        // Token generation.
        JWToken jwToken = new JWToken(account.getName(), account.getId(), account.getRole(), ipAddress);
        String tokenString = jwToken.encode(this.webConfig.getIssuer(),
                this.webConfig.getPassphrase(),
                this.webConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(account);
    }
}
