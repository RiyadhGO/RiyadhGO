package sa.edu.yamama.riyadhgo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
RiyadhGo uses MVC (Model View Controller) architecture, 
the main responsibilities of controllers include intercepting incoming requests, converting the payload of the request to the internal structure of the data
sending the data to Model for further processing, getting processed data from the Model, and advancing that data to the View for rendering
 */
@RestController
public class HelloController {
    

    @RequestMapping("/") // http request to validate connection
    public String index() {
        return "Hello World!";//connect to database --> retreive data --> return data
    }
}
