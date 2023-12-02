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

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        var found = this.locationRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create")
    public ResponseEntity<Location> add(@RequestBody Location item) {
        var saved = this.locationRepository.save(item);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Location> view(@PathVariable("id") Long id) {
        var found = this.locationRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Location> update(@PathVariable("id") Long id, @RequestBody Location item) {
        var found = this.locationRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude());
            old.setLongitude(item.getLatitude());
            old.setName(item.getName());
            old.setLocationType(item.getLocationType());
            var updated = this.locationRepository.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.locationRepository.findById(id);
        if (found.isPresent()) {
            this.locationRepository.delete(found.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{type}")
    public ResponseEntity<List<Location>> findByLatLng(@PathVariable("type") String type) {
        var found = this.locationRepository.findAllByLocationType(TransportMethodTypes.valueOf(type));
        if (found != null) {
            return ResponseEntity.ok().body(found);
        }
        return ResponseEntity.notFound().build();
    }

}
