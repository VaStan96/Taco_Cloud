package client.taco_client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IngredientController {

    @GetMapping("/admin/home")
    public String getHome(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "home";
    }
}
