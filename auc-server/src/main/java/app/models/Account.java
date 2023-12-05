package app.models;

import com.fasterxml.jackson.annotation.JsonView;

import app.security.SecureHasher;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Account {

  @Id
  @SequenceGenerator(name = "user_generator")
  @GeneratedValue(generator = "user_generator", strategy = GenerationType.SEQUENCE)
  @JsonView(ViewClasses.Summary.class)
  private long id;

  @JsonView(ViewClasses.Summary.class)
  private String name;

  @JsonView(ViewClasses.Summary.class)
  private String email;

  private String hashedPassword = null;

  @JsonView(ViewClasses.Summary.class)
  private String role;

  public Account(String email, String role) {
    this.name = email.split("@")[0];
    this.email = email;
    this.role = role;
  }

  public Account() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String newEmail) {
    this.email = newEmail;
  }

  public String hashPassword(String password) {
    return SecureHasher.secureHash(password);
  }

  public void setPassword(String newPassword) {
    this.setHashedPassword(this.hashPassword(newPassword));
  }

  public boolean verifyPassword(String password) {
    return this.hashedPassword.equals(this.hashPassword(password));
  }

  public String getHashedPassword() {
    return this.hashedPassword;
  }

  public void setHashedPassword(String newHashedPassword) {
    this.hashedPassword = newHashedPassword;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String newRole) {
    this.role = newRole;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;

    if (!(o instanceof Account)) {
      return false;
    }

    Account user = (Account) o;
    return id == user.id && name.equals(user.name) && email.equals(user.email)
        && hashedPassword.equals(user.hashedPassword) && role.equals(user.role);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(id) + name.hashCode() + email.hashCode() + hashedPassword.hashCode() + role.hashCode();
  }
}
