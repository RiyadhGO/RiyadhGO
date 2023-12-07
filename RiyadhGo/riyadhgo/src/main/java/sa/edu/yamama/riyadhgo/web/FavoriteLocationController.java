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

import sa.edu.yamama.riyadhgo.domain.FavoriteLocation;
import sa.edu.yamama.riyadhgo.repo.FavoriteLocationRepository;
import sa.edu.yamama.riyadhgo.security.RiyadhgoAuthService;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities for controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */
@RestController
@RequestMapping("/favlocs") // http request call named favlocs 
public class FavoriteLocationController {

    @Autowired
    private FavoriteLocationRepository favLocRepo;

    @Autowired
    private RiyadhgoAuthService authService;

    @GetMapping
    public ResponseEntity<List<FavoriteLocation>> getAll() { //responds by retrieving information stored in favourite location
        var found = this.favLocRepo.findAll(); //finding all favourite locations stored in the repository
        return ResponseEntity.ok().body(found); //returning favourite locations to the user
    }

    @PostMapping("/create") // http request for the creation of new favorite location by user
    public ResponseEntity<FavoriteLocation> add(@RequestBody FavoriteLocation item) { //responds by retrieving items stored in favourite location
        var saved = this.favLocRepo.save(item); 
        return ResponseEntity.ok().body(saved); //stores new favourite location
    }

    @GetMapping("/edit/{id}") // http request for viewing the new favourite location
    public ResponseEntity<FavoriteLocation> view(@PathVariable("id") Long id) {
        var found = this.favLocRepo.findById(id); //finding the favourite location in the repository using the favourite location id 
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); //if found return the favourite location to user
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}") // http request for updating the favourite location
    public ResponseEntity<FavoriteLocation> update(@PathVariable("id") Long id, @RequestBody FavoriteLocation item) {
        var found = this.favLocRepo.findById(id); //finding the favourite location in the repository using the favourite location id 
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude()); //overwrite old longitude and latitude with the new values
            old.setLongitude(item.getLatitude());
            old.setName(item.getName()); 
            var updated = this.favLocRepo.save(old);
            return ResponseEntity.ok().body(updated); //favourite location is updated

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}") // http request for deleting a favourite location
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {  //responds by retrieving items stored in favourite location using id
        var found = this.favLocRepo.findById(id);
        if (found.isPresent()) {
            this.favLocRepo.delete(found.get()); 
            return ResponseEntity.ok().build(); // favourite location is deleted when found
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add") // http request for adding a favourite location
    public ResponseEntity<FavoriteLocation> addToFavorites(@RequestBody FavoriteLocation item) { 
        var user = this.authService.getCurrentUser(); // authenticating user request
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        item.setUserId(user.getUserId());
        var saved = this.favLocRepo.save(item);
        return ResponseEntity.ok(saved); // favourite location is added

    }

    @PostMapping("/remove") // http request for removing a favourite place
    public ResponseEntity<FavoriteLocation> removeFromFavorites(@RequestBody FavoriteLocation item) { 
        var user = this.authService.getCurrentUser(); //authenticating user request
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        var found = this.favLocRepo.findAllByUserId(user.getUserId()); //finding user's favourite locations by user id
        if (found != null && found.size() > 0) {
            var existing = found.stream()
                    .filter(l -> {
                        return l.getName().equals(item.getName());
                    })
                    .findFirst();
            if (existing.isPresent()) {
                this.favLocRepo.delete(item);
                return ResponseEntity.ok().build(); // if items are found then remove
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/me") // http request for displaying user's favourite locations
    public ResponseEntity<List<FavoriteLocation>> myFavorites() { 
        var user = this.authService.getCurrentUser(); //authenticating user
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        var found = this.favLocRepo.findAllByUserId(user.getUserId()); //find all favourite locations by user id
        return ResponseEntity.ok(found);
    }

}
