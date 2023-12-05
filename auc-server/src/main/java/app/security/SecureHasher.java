package app.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecureHasher {

  private static MessageDigest saltedMessageDigest = getMessageDigest("SHA-512");
  private static String secretSalt = "HvA-HBO-ICT-2022";

  /**
   * Get a MessageDigest instance for a given algorithm.
   * 
   * @param algorithm the algorithm to get a MessageDigest instance for
   * @return the MessageDigest instance
   */
  private static MessageDigest getMessageDigest(String algorithm) {
    try {
      // Apply the secret salt to the algorithm.
      MessageDigest md = MessageDigest.getInstance(algorithm);

      // Append the secret salt to the algorithm.
      md.update(String.format("%-16s", secretSalt).getBytes(StandardCharsets.UTF_8));

      // Reset the MessageDigest instance to its initial state.
      md.reset();

      return md;
    } catch (NoSuchAlgorithmException e) {
      if (!algorithm.equals("SHA-256")) {
        return getMessageDigest("SHA-256");
      }
    }

    return null;
  }

  /**
   * Calculate a secure hash from a source for the purpose of password hashing.
   * 
   * @param source the source string to be hashed
   * @return the hashed result
   */
  public static String secureHash(String source) {
    if (saltedMessageDigest == null || secretSalt == null) {
      return null;
    }

    // Calculate the hash of the source string with the secret salt appended to it.
    byte[] hashedResult = saltedMessageDigest.digest((source + secretSalt).getBytes());

    return Base64.getEncoder().encodeToString(hashedResult); // Encode the result in Base64 and return it.
  }
}
