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

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private RiyadhgoAuthService authService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthModel info) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(info.getEmail(),
                info.getPassword(),
                null);
        var authenticated = this.authService.authenticate(auth);
        if (authenticated == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var token = this.jwtTokenUtil.generateToken((User) authenticated.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthModel> add(@RequestBody AuthModel item) {

        var user = new User();
        user.setEmail(item.getEmail());
        user.setName(item.getName());
        user.setPassword(item.getPassword());
        user.setRole(UserRoleNames.USER);
        user.setPhone(item.getPhone());
        
        var saved = authService.registerUser(user);
        if (saved != null && saved.getUserId() > 0) {
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.badRequest().body(item);        
    }
}
