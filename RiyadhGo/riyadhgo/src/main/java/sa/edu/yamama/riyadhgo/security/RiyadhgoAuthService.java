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

@Service
public class RiyadhgoAuthService implements AuthenticationProvider {

       private Logger logger = LoggerFactory.getLogger(RiyadhgoAuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();// password

        var found = userRepository.findOneByEmail(username);
        if (found.isPresent()) {
            User user = found.get();
            String encodedSalt = user.getPasswordSalt();
            String hashedPassword = this.getHashedValue(password, encodedSalt);
            if (user.getPassword().equals(hashedPassword)) {
                
                var u = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
                var ctx = SecurityContextHolder.getContext();
                ctx.setAuthentication(u);
                SecurityContextHolder.setContext(ctx);
                return u;
            }
        }

        return null;
    }

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

    public User registerUser(User user) {
        
        String encodedSalt = this.getEncodedSalt();
        String hashedPassword = this.getHashedValue(user.getPassword(), encodedSalt);
        user.setPassword(hashedPassword);
        user.setPasswordSalt(encodedSalt);

        var added = userRepository.save(user);
     
        return added;
    }

    private String getEncodedSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        return encodedSalt;
    }

    public boolean isRegisteredBefore(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }

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
