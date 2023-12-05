package app.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotAcceptable;
import org.springframework.web.server.NotAcceptableStatusException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import app.exceptions.NotAcceptableException;
import app.models.Account;
import app.repositories.EntityRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  @Autowired
  EntityRepository<Account> usersRepo;

  /**
   * Login a user.
   * 
   * @param loginInfo
   * @throws
   * @return
   */
  // @PostMapping("/login")
  // public EntityRepository postMethodName(@RequestBody ObjectNode loginInfo,
  // HttpServletRequest request)
  // throws NotAcceptableException {

  // String username = loginInfo.get("username").asText();
  // String password = loginInfo.get("password").asText();

  // }

}
