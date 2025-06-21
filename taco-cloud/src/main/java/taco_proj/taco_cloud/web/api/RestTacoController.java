package taco_proj.taco_cloud.web.api;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import taco_proj.taco_cloud.data.TacoRepository;
import taco_proj.taco_cloud.entity.Taco;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path="/api/tacos", produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
public class RestTacoController {

    private final TacoRepository tacoRepository;

    public RestTacoController (TacoRepository tacoRepository){
        this.tacoRepository = tacoRepository;
    }
    
    @GetMapping(params="recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoByID(@PathVariable("id") Long id) {
        // get the taco as an option-object
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if (optTaco.isPresent()){
            // return the object-ResponseEntity with taco and status-code 200
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    // set only json data
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    // auto serialization JSON in body -> Taco with @RequestBody
    public Taco postTaco(@RequestBody Taco taco) {     
        return tacoRepository.save(taco);
    }
}
