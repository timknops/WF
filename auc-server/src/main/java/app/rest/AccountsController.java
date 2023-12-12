package app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import app.exceptions.ResourceNotFoundException;
import app.models.Account;
import app.models.ViewClasses;
import app.repositories.EntityRepository;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

  @Autowired
  EntityRepository<Account> accountsRepo;

  @JsonView(ViewClasses.Summary.class)
  @GetMapping(path = "", produces = "application/json")
  public List<Account> getAllAccounts() {
    return this.accountsRepo.findAll();
  }

  @GetMapping(path = "{id}", produces = "application/json")
  public ResponseEntity<Account> getOneAccount(@PathVariable() long id) {
    Account account = this.accountsRepo.findById(id);

    if (account == null) {
      throw new ResourceNotFoundException("Cannot provide a account with id=" + id);
    }

    return ResponseEntity.ok().body(account);
  }

  // @DeleteMapping(path = "{id}")
  // public Account deleteOneAccount(@PathVariable() long id,
  // @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {

  // if (jwtInfo == null || !jwtInfo.isAdmin()) {
  // throw new UnAuthorizedException(
  // "Admin role is required to remove an account");
  // }
  // Account account = this.accountsRepo.deleteById(id);

  // if (account == null) {
  // throw new ResourceNotFoundException("Cannot delete an account with id=" +
  // id);
  // }

  // return account;
  // }

}
