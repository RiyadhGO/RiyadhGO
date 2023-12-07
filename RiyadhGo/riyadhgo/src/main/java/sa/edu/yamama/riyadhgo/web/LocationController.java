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

import sa.edu.yamama.riyadhgo.domain.Location;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;
import sa.edu.yamama.riyadhgo.repo.LocationRepository;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */

@RestController
@RequestMapping("/locations") 
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public ResponseEntity<List<Location>> getAll() { //a response call to the location repository
        var found = this.locationRepository.findAll();
        return ResponseEntity.ok().body(found); 
    }

    @PostMapping("/create") // http request to create a location item
    public ResponseEntity<Location> add(@RequestBody Location item) { //responds by adding to items stored in location
        var saved = this.locationRepository.save(item);
        return ResponseEntity.ok().body(saved); // saving the location in the repository
    }

    @GetMapping("/edit/{id}") // http request to edit location
    public ResponseEntity<Location> view(@PathVariable("id") Long id) { //responds by viewing items stored in location
        var found = this.locationRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); //if found allow edit
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}") // http request to edit the coordinate of the location and update it
    public ResponseEntity<Location> update(@PathVariable("id") Long id, @RequestBody Location item) { 
        var found = this.locationRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude()); // overwrite old longitude and latitudes with new values
            old.setLongitude(item.getLatitude());
            old.setName(item.getName());
            old.setLocationType(item.getLocationType()); //setting the location type
            var updated = this.locationRepository.save(old); //overwrite the old location save with the new location
            return ResponseEntity.ok().body(updated); //location is updated

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}") // http request to delete a location
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {  //responds by deleting items stored in location
        var found = this.locationRepository.findById(id); // finding the item in the location repository by id
        if (found.isPresent()) {
            this.locationRepository.delete(found.get());
            return ResponseEntity.ok().build(); // if found it will be deleted
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{type}") // http request to find a location
    public ResponseEntity<List<Location>> findByLatLng(@PathVariable("type") String type) { // once type is selected it will execute find by latitude and longitude
        var found = this.locationRepository.findAllByLocationType(TransportMethodTypes.valueOf(type)); //finding the location with the selected transport method type in the location repository
        if (found != null) {
            return ResponseEntity.ok().body(found); //if found it will be displayed
        }
        return ResponseEntity.notFound().build();
    }

}
