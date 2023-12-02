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

@RestController
@RequestMapping("/favlocs")
public class FavoriteLocationController {

    @Autowired
    private FavoriteLocationRepository favLocRepo;

    @Autowired
    private RiyadhgoAuthService authService;

    @GetMapping
    public ResponseEntity<List<FavoriteLocation>> getAll() {
        var found = this.favLocRepo.findAll();
        return ResponseEntity.ok().body(found);
    }

    @PostMapping("/create")
    public ResponseEntity<FavoriteLocation> add(@RequestBody FavoriteLocation item) {
        var saved = this.favLocRepo.save(item);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<FavoriteLocation> view(@PathVariable("id") Long id) {
        var found = this.favLocRepo.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FavoriteLocation> update(@PathVariable("id") Long id, @RequestBody FavoriteLocation item) {
        var found = this.favLocRepo.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setLatitude(item.getLatitude());
            old.setLongitude(item.getLatitude());
            old.setName(item.getName());
            var updated = this.favLocRepo.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.favLocRepo.findById(id);
        if (found.isPresent()) {
            this.favLocRepo.delete(found.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<FavoriteLocation> addToFavorites(@RequestBody FavoriteLocation item) {
        var user = this.authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        item.setUserId(user.getUserId());
        var saved = this.favLocRepo.save(item);
        return ResponseEntity.ok(saved);

    }

    @PostMapping("/remove")
    public ResponseEntity<FavoriteLocation> removeFromFavorites(@RequestBody FavoriteLocation item) {
        var user = this.authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        var found = this.favLocRepo.findAllByUserId(user.getUserId());
        if (found != null && found.size() > 0) {
            var existing = found.stream()
                    .filter(l -> {
                        return l.getName().equals(item.getName());
                    })
                    .findFirst();
            if (existing.isPresent()) {
                this.favLocRepo.delete(item);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<FavoriteLocation>> myFavorites() {
        var user = this.authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        var found = this.favLocRepo.findAllByUserId(user.getUserId());
        return ResponseEntity.ok(found);
    }

}
