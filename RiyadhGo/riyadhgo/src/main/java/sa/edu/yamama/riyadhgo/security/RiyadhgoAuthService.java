package sa.edu.yamama.riyadhgo.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sa.edu.yamama.riyadhgo.domain.User;
import sa.edu.yamama.riyadhgo.repo.UserRepository;
//Importing an Authentication Provider from the spring security framework 
@Service
public class RiyadhgoAuthService implements AuthenticationProvider {

       private Logger logger = LoggerFactory.getLogger(RiyadhgoAuthService.class);

    @Autowired
    private UserRepository userRepository;
//Authenticating usernames and passwords
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); // Username is submitted by user
        String password = authentication.getCredentials().toString();// password is submitted by user

        var found = userRepository.findOneByEmail(username); // Finding the username in the user repository 
        if (found.isPresent()) {
            User user = found.get();
            String encodedSalt = user.getPasswordSalt(); // Retrieving the password salt
            String hashedPassword = this.getHashedValue(password, encodedSalt); // Adding the salt to the password entered by the user to get the hashed value
            if (user.getPassword().equals(hashedPassword)) { // Comparing the hashed value to the password entered by user 
                
                var u = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities()); //Creation of username and password authentication token
                var ctx = SecurityContextHolder.getContext();
                ctx.setAuthentication(u);
                SecurityContextHolder.setContext(ctx);
                return u;
            }
        }

        return null;
    }
// Method for hashing the password
   @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public String getHashedValue(String data, String salt) {
        byte[] hashedValue = null;

        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), 5000, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedValue = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(hashedValue);
    }
//The register user method
    public User registerUser(User user) {
        
        String encodedSalt = this.getEncodedSalt(); //generate password salt
        String hashedPassword = this.getHashedValue(user.getPassword(), encodedSalt); //apply password salt to the password entered by user
        user.setPassword(hashedPassword); //set the hashed password
        user.setPasswordSalt(encodedSalt); //set the encoded salt 

        var added = userRepository.save(user); //save to user repository
     
        return added;
    }
//The get encoded salt method to generate password salt
    private String getEncodedSalt() {
        SecureRandom random = new SecureRandom(); // SecureRandom is security class that provides a cryptographically strong random number generator (RNG)
        byte[] salt = new byte[16];
        random.nextBytes(salt); // the random number generator is assigned to the name 'random' and applied to the salt 
        String encodedSalt = Base64.getEncoder().encodeToString(salt); // converting the 16 byte number to a string and storing it as 'encodedSalt'
        return encodedSalt;
    }
//The boolean method confirms if the user is previously registered using the email entered.
    public boolean isRegisteredBefore(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }
//The method retrieves the current user
    public User getCurrentUser() {
        var ctx = SecurityContextHolder.getContext();
        var auth = ctx.getAuthentication();
        if (auth != null) {
            var user = (User) auth.getPrincipal();
            return user;
        }
        return null;
    }

}
