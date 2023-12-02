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



@RequestMapping("/users")
@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiyadhgoAuthService authService;


    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        var found = this.userRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create")
    public ResponseEntity<User> add(@RequestBody User item) {

        var saved = authService.registerUser(item);
        if (saved != null && saved.getUserId() > 0) {
            return ResponseEntity.ok().body(saved);
        }
        return ResponseEntity.badRequest().body(item);        
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<User> view(@PathVariable("id") Long id) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User item) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setName(item.getName());
            var updated = this.userRepository.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.userRepository.findById(id);
        if (found.isPresent()) {
            this.userRepository.delete(found.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
