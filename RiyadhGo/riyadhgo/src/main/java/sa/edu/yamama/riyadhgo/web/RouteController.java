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
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */


@RequestMapping("/routes")
@RestController
public class RouteController {
    

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping // http request to recieve all routes in the route repository
    public ResponseEntity<List<Route>> getAll() {
        var found = this.routeRepository.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create") // http request to create a route
    public ResponseEntity<Route> add(@RequestBody Route item) { // reponds by adding a route item to the repository found by id
        var saved = this.routeRepository.save(item);
        return ResponseEntity.ok().body(saved); // saving the created route
    }

    @GetMapping("/edit/{id}") // http request to edit a route
    public ResponseEntity<Route> view(@PathVariable("id") Long id) { // reponds by displaying route items from the repository found by id
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); //if found allow edit
        }
        return ResponseEntity.notFound().build();
    }

    
    @PutMapping("/edit/{id}") // http request to update a route
    public ResponseEntity<Route> update(@PathVariable("id") Long id, @RequestBody Route item) { // responds by displaying route items from the repository found by id
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude());// overwrite the old longitude and latitude with the new route values
            old.setLongitude(item.getLongitude());
            old.setTime(item.getTime()); 
            old.setTripId(item.getTripId());
            var updated = this.routeRepository.save(old); //overwrite the old trip save with the new trip
            return ResponseEntity.ok().body(updated); //trip is update

        }

        return ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}") // http request to delete a route
    public ResponseEntity<String> delete(@PathVariable("id") Long id) { // responds by deleting a route item from the repository found by id
        var found = this.routeRepository.findById(id);
        if (found.isPresent()) {
            this.routeRepository.delete(found.get()); 
            return ResponseEntity.ok().build(); // if found delete route
        }
        return ResponseEntity.notFound().build();
    }

}
