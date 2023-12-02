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

import sa.edu.yamama.riyadhgo.domain.Route;
import sa.edu.yamama.riyadhgo.repo.RouteRepository;



@RequestMapping("/routes")
@RestController
public class RouteController {
    

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public ResponseEntity<List<Route>> getAll() {
        var found = this.routeRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create")
    public ResponseEntity<Route> add(@RequestBody Route item) {
        var saved = this.routeRepository.save(item);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Route> view(@PathVariable("id") Long id) {
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}")
    public ResponseEntity<Route> update(@PathVariable("id") Long id, @RequestBody Route item) {
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude());// (item.getLocationId());
            old.setLongitude(item.getLongitude());
            old.setTime(item.getTime());
            old.setTripId(item.getTripId());
            var updated = this.routeRepository.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            this.routeRepository.delete(found.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
