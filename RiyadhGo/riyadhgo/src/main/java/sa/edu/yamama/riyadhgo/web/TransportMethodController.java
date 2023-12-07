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
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */

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

    @GetMapping("/select") // http request for selecting a transport method
    public ResponseEntity<List<SelectTransportMethodModel>> selectOptions() { // reponds by listing the select options for the transport methods
        List<SelectTransportMethodModel> models = new ArrayList<>();
        var found = this.methodRepository.findAll();
        if (found != null && found.size() > 0) {
            Map<String, SelectTransportMethodModel> collected = new HashMap<>(); 

            for (var item : found) {
                var type = item.getMethodType() != null ? item.getMethodType().toString()
                        : TransportMethodTypes.WALK.toString(); //if walking is selected the information associated with the method type will be displayed
                if (!collected.containsKey(type)) {

                    var model = new SelectTransportMethodModel();
                    model.setMethodType(type);
                    model.setNames(item.getName());
                    model.setMaxPrice(item.getCostPerUnit()); //finding the max price for the method type
                    model.setMinPrice(item.getCostPerUnit()); //finding the min price for the method type
                    collected.put(type, model); //displaying the method type with the information

                } else {

                    var old = collected.get(type);
                    old.setNames(old.getNames() + ", " + item.getName());
                    if (old.getMaxPrice() < item.getCostPerUnit()) {
                        old.setMaxPrice(item.getCostPerUnit()); // setting max price for the type of transport method
                    }
                    if (old.getMinPrice() > item.getCostPerUnit()) {
                        old.setMinPrice(item.getCostPerUnit()); // setting min price for the type of transport method
                    }
                }

            }
            for (var k : collected.keySet()) {
                models.add(collected.get(k));
            }
        }
        return ResponseEntity.ok().body(models);
    }

    @GetMapping("/stat/{type}") // http request to find statistics of the transport method type
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
                model.setMaxPrice(item.getCostPerUnit()); //finding the max price for the method type
                model.setMinPrice(item.getCostPerUnit()); //finding the min price for the method type

                if (model.getMaxPrice() < item.getCostPerUnit()) { // setting max price for the type of transport method
                    model.setMaxPrice(item.getCostPerUnit());
                }
                if (model.getMinPrice() > item.getCostPerUnit()) { // setting min price for the type of transport method
                    model.setMinPrice(item.getCostPerUnit());
                }
            }

        }
        return ResponseEntity.ok().body(model); // return the statistics of the transport method selected
    }

    @GetMapping("/find/{type}") // http request to find a type of transport method
    public ResponseEntity<List<TransportMethod>> findMethodsByType(@PathVariable("type") String type) {
        var mType = TransportMethodTypes.valueOf(type);
        return ResponseEntity.ok(this.methodRepository.findAllByMethodType(mType)); //display transport methods found
    }

    @PostMapping("/create") // http request to create a transport method
    public ResponseEntity<TransportMethod> add(@RequestBody TransportMethod item) { //responds by adding a transport method item to the repository
        var saved = this.methodRepository.save(item);
        return ResponseEntity.ok().body(saved); //transport method is created
    }

    @GetMapping("/edit/{id}") // http request to view transport method
    public ResponseEntity<TransportMethod> view(@PathVariable("id") Long id) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            return ResponseEntity.ok().body(found.get()); // if found by id transport method is displayed
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}") // http request to update the transport method
    public ResponseEntity<TransportMethod> update(@PathVariable("id") Long id, @RequestBody TransportMethod item) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            var old = found.get();
            old.setCarbonEmitted(item.isCarbonEmitted()); //overwrite old transport method values with new ones
            old.setCostPerUnit(item.getCostPerUnit());
            old.setLink(item.getLink());
            old.setMeasureUnit(item.getMeasureUnit());
            old.setName(item.getName());
            old.setMethodType(item.getMethodType());
            var updated = this.methodRepository.save(old); //overwrite the old transport method 
            return ResponseEntity.ok().body(updated); //transport method is updated

        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}") // http request to delete a transport method
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        var found = this.methodRepository.findById(id);
        if (found.isPresent()) {
            this.methodRepository.deleteById(id);
            return ResponseEntity.ok().build(); //if found transport method is deleted
        }
        return ResponseEntity.notFound().build();
    }

}
