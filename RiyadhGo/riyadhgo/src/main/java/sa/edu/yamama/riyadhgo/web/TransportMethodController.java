package sa.edu.yamama.riyadhgo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import sa.edu.yamama.riyadhgo.domain.TransportMethod;
import sa.edu.yamama.riyadhgo.domain.TransportMethodTypes;
import sa.edu.yamama.riyadhgo.models.SelectTransportMethodModel;
import sa.edu.yamama.riyadhgo.repo.TransportMethodRepository;

@RestController
@RequestMapping("/tm")
public class TransportMethodController {

    @Autowired
    private TransportMethodRepository methodRepository;

    @GetMapping
    public ResponseEntity<List<TransportMethod>> getAll() {
        var found = this.methodRepository.findAll();

        return ResponseEntity.ok().body(found);
    }

    @GetMapping("/select")
    public ResponseEntity<List<SelectTransportMethodModel>> selectOptions() {
        List<SelectTransportMethodModel> models = new ArrayList<>();
        var found = this.methodRepository.findAll();
        if (found != null && found.size() > 0) {
            Map<String, SelectTransportMethodModel> collected = new HashMap<>();

            for (var item : found) {
                var type = item.getMethodType() != null ? item.getMethodType().toString()
                        : TransportMethodTypes.WALK.toString();
                if (!collected.containsKey(type)) {

                    var model = new SelectTransportMethodModel();
                    model.setMethodType(type);
                    model.setNames(item.getName());
                    model.setMaxPrice(item.getCostPerUnit());
                    model.setMinPrice(item.getCostPerUnit());
                    collected.put(type, model);

                } else {

                    var old = collected.get(type);
                    old.setNames(old.getNames() + ", " + item.getName());
                    if (old.getMaxPrice() < item.getCostPerUnit()) {
                        old.setMaxPrice(item.getCostPerUnit());
                    }
                    if (old.getMinPrice() > item.getCostPerUnit()) {
                        old.setMinPrice(item.getCostPerUnit());
                    }
                }

            }
            for (var k : collected.keySet()) {
                models.add(collected.get(k));
            }
        }
        return ResponseEntity.ok().body(models);
    }

    @GetMapping("/stat/{type}")
    public ResponseEntity<SelectTransportMethodModel> selectTypeStatistics(@PathVariable("type") String ttype) {
        var mType = TransportMethodTypes.valueOf(ttype);
        SelectTransportMethodModel model = new SelectTransportMethodModel();
        model.setMethodType("All " + ttype);
        var found = this.methodRepository.findAll();
        if (found != null && found.size() > 0) {

            for (var item : found) {
                if (item.getMethodType() != mType) {
                    continue;
                }

                model.setNames(item.getName());
                model.setMaxPrice(item.getCostPerUnit());
                model.setMinPrice(item.getCostPerUnit());

                if (model.getMaxPrice() < item.getCostPerUnit()) {
                    model.setMaxPrice(item.getCostPerUnit());
                }
                if (model.getMinPrice() > item.getCostPerUnit()) {
                    model.setMinPrice(item.getCostPerUnit());
                }
            }

        }
        return ResponseEntity.ok().body(model);
    }

    @GetMapping("/find/{type}")
    public ResponseEntity<List<TransportMethod>> findMethodsByType(@PathVariable("type") String type) {
        var mType = TransportMethodTypes.valueOf(type);
        return ResponseEntity.ok(this.methodRepository.findAllByMethodType(mType));
    }

    @PostMapping("/create")
    public ResponseEntity<TransportMethod> add(@RequestBody TransportMethod item) {
        var saved = this.methodRepository.save(item);
        return ResponseEntity.ok().body(saved);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<TransportMethod> view(@PathVariable("id") Long id) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TransportMethod> update(@PathVariable("id") Long id, @RequestBody TransportMethod item) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setCarbonEmitted(item.isCarbonEmitted());
            old.setCostPerUnit(item.getCostPerUnit());
            old.setLink(item.getLink());
            old.setMeasureUnit(item.getMeasureUnit());
            old.setName(item.getName());
            old.setMethodType(item.getMethodType());
            var updated = this.methodRepository.save(old);
            return ResponseEntity.ok().body(updated);

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            this.methodRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
