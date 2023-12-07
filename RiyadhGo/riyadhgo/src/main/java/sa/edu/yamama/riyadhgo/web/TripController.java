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


import sa.edu.yamama.riyadhgo.domain.Trip;
import sa.edu.yamama.riyadhgo.repo.TripRepository;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */
@RequestMapping("/trips")
@RestController
public class TripController {
    

    @Autowired
    private TripRepository tripRepository;



    @GetMapping //http request to get all items in the trip repository
    public ResponseEntity<List<Trip>> getAll() {
        var found = this.tripRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create") // http request to create a trip item
    public ResponseEntity<Trip> add(@RequestBody Trip item) { // responds by adding a trip item to the repository
        var saved = this.tripRepository.save(item);
        return ResponseEntity.ok().body(saved); // new trip is created
    }

    @GetMapping("/edit/{id}") // http request to view trips
    public ResponseEntity<Trip> view(@PathVariable("id") Long id) { // responds by viewing all trips in the repository by id
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); // if trip is found it will be displayed
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}") // http request to update trip
    public ResponseEntity<Trip> update(@PathVariable("id") Long id, @RequestBody Trip item) {
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setDestLat(item.getDestLat()); //overwrite ld trip values with new ones
            old.setDestLng(item.getDestLng());
            old.setDuration(item.getDuration());
            old.setEndTime(item.getEndTime());
            old.setStartLat(item.getStartLat());
            old.setStartLng(item.getStartLng());
            old.setStartTime(item.getStartTime());
            old.setTransportMethodId(item.getTransportMethodId());
            old.setUserId(item.getUserId());
            var updated = this.tripRepository.save(old); // overwrite old trip save
            return ResponseEntity.ok().body(updated); // trip is updated

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}") // http request to delete a trip
    public ResponseEntity<String> delete(@PathVariable("id") Long id) { //responds by deleting an item in trip repository by id
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            this.tripRepository.delete(found.get()); // if trip is found it will be deleted
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
