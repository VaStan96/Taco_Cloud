package client.taco_client;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/admin")
public class RestIngredientController {
    private final RestTemplate restTemplate;

    public RestIngredientController(OAuth2AuthorizedClientService clientService) {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getInterceptors().add(new BearerInterceptor(clientService));
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAll() {
        String url = "http://localhost:8080/api/ingredients";
        Ingredient[] result_array = restTemplate.getForObject(url, Ingredient[].class);
        List<Ingredient> result = Arrays.asList(result_array);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@ModelAttribute Ingredient ingredient) {
        String url = "http://localhost:8080/api/ingredients";
        Ingredient result = restTemplate.postForObject(url, ingredient, Ingredient.class);
        return ResponseEntity.ok(result);
    }

}
