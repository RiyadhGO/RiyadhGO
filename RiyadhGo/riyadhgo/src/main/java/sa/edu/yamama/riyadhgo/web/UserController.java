package sa.edu.yamama.riyadhgo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sa.edu.yamama.riyadhgo.domain.User;
import sa.edu.yamama.riyadhgo.repo.UserRepository;
import sa.edu.yamama.riyadhgo.security.RiyadhgoAuthService;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */


@RequestMapping("/users")
@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiyadhgoAuthService authService;


    @GetMapping // http request listing all users
    public ResponseEntity<List<User>> getAll() {
        var found = this.userRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create") // http request to create a user
    public ResponseEntity<User> add(@RequestBody User item) { //responds by adding a new item to user repository

        var saved = authService.registerUser(item); //authenticating user
        if (saved != null && saved.getUserId() > 0) {
            return ResponseEntity.ok().body(saved); //saving user id
        }
        return ResponseEntity.badRequest().body(item);        
    }

    @GetMapping("/edit/{id}") //http request to view users
    public ResponseEntity<User> view(@PathVariable("id") Long id) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); //if user is found it will be displayed
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}") // http request to update user 
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User item) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setName(item.getName()); //overwrite old user name
            var updated = this.userRepository.save(old); //overwrite old user save
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}") // http request to delete user
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            this.userRepository.delete(found.get());
            return ResponseEntity.ok().build(); // if found user is deleted
        }
        return ResponseEntity.notFound().build();
    }

}
