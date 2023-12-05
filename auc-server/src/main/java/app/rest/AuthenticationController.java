package app.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotAcceptable;
import org.springframework.web.server.NotAcceptableStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.WebConfig;
import app.exceptions.NotAcceptableException;
import app.models.Account;
import app.models.ViewClasses;
import app.repositories.EntityRepository;
import jakarta.servlet.http.HttpServletRequest;

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
    EntityRepository<Account> accountsRepo;

    @JsonView(ViewClasses.Summary.class)
    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody ObjectNode loginInfo,
            HttpServletRequest request) throws NotAcceptableException {

        String username = loginInfo.get("email").asText();
        String password = loginInfo.get("password").asText();

        Account account = accountsRepo.findAll().stream().filter(a -> a.getEmail().equals(username))
                .findFirst().orElse(null);

        if (account == null || !account.verifyPassword(password)) {
            throw new NotAcceptableException("Invalid username or password.");
        }

        // TODO add token generation
        // TODO add token string to response header
        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + "token")
                .body(account);
    }
}
