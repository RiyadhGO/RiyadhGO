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

@RequestMapping("/trips")
@RestController
public class TripController {
    

    @Autowired
    private TripRepository tripRepository;



    @GetMapping
    public ResponseEntity<List<Trip>> getAll() {
        var found = this.tripRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create")
    public ResponseEntity<Trip> add(@RequestBody Trip item) {
        var saved = this.tripRepository.save(item);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Trip> view(@PathVariable("id") Long id) {
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}")
    public ResponseEntity<Trip> update(@PathVariable("id") Long id, @RequestBody Trip item) {
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setDestLat(item.getDestLat());
            old.setDestLng(item.getDestLng());
            old.setDuration(item.getDuration());
            old.setEndTime(item.getEndTime());
            old.setStartLat(item.getStartLat());
            old.setStartLng(item.getStartLng());
            old.setStartTime(item.getStartTime());
            old.setTransportMethodId(item.getTransportMethodId());
            old.setUserId(item.getUserId());
            var updated = this.tripRepository.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.tripRepository.findById(id);
        if (found.isPresent()) {
            this.tripRepository.delete(found.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
