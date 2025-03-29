package taco_proj.taco_cloud.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import taco_proj.taco_cloud.Ingredient;
import taco_proj.taco_cloud.Ingredient.Type;
import taco_proj.taco_cloud.Taco;
import taco_proj.taco_cloud.TacoOrder;
import taco_proj.taco_cloud.data.IngredientRepository;



@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }
    
    // add object in Model
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        // Ingredients aus "DB"
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        // filter by types
        Type[] types = Ingredient.Type.values();
        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    // add object in Model
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    // add object in Model
    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    // Get request
    @GetMapping
    public String showDesignForm() {
        //return View from resources/template
        return "design";
    }

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type){
        List<Ingredient> list = new ArrayList<>();
        ingredients.forEach(list::add);
        return list.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    // checking validate for object Taco from View and adding errors in object Errors
    // TacoOrder from Model
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){
        
        if (errors.hasErrors()){
            //return View from resources/template
            int c = errors.getErrorCount();
            for (int i =0; i<c; i++){
                log.warn("!!!!!!!!!!Errors - {}", errors.getAllErrors().get(i));
            }
            
            return "design";
        }
        
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
    
}
