package sa.edu.yamama.riyadhgo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    

    @RequestMapping("/")
    public String index() {
        return "Hello World!";//connect to database --> retreive data --> return data
    }
}
