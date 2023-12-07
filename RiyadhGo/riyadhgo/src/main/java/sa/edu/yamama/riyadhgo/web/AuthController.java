package sa.edu.yamama.riyadhgo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa.edu.yamama.riyadhgo.domain.User;
import sa.edu.yamama.riyadhgo.models.AuthModel;
import sa.edu.yamama.riyadhgo.security.JwtTokenUtil;
import sa.edu.yamama.riyadhgo.security.RiyadhgoAuthService;
import sa.edu.yamama.riyadhgo.security.UserRoleNames;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private RiyadhgoAuthService authService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login") // http request call named login
    public ResponseEntity<String> login(@RequestBody AuthModel info) { //responds by retrieving information stored in the AuthModel
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(info.getEmail(), // Using the username password authentication token on the credentials submitted
                info.getPassword(),
                null);
        var authenticated = this.authService.authenticate(auth);
        if (authenticated == null) { 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // in case of a mismatch, the authentication is failed and user is not granted access
        }
        var token = this.jwtTokenUtil.generateToken((User) authenticated.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(token); // if the authentication is successful, the user is granted access
    }
    
    @PostMapping("/register") // http request call named register
    public ResponseEntity<AuthModel> add(@RequestBody AuthModel item) { //responds by retrieving the items stored in the AuthModel

        var user = new User();
        user.setEmail(item.getEmail());
        user.setName(item.getName());
        user.setPassword(item.getPassword());
        user.setRole(UserRoleNames.USER);
        user.setPhone(item.getPhone());
        
        var saved = authService.registerUser(user); 
        if (saved != null && saved.getUserId() > 0) {
            return ResponseEntity.ok().body(null); // if the credentials are filled properly
        }
        return ResponseEntity.badRequest().body(item); // if one item is not filled       
    }
}
