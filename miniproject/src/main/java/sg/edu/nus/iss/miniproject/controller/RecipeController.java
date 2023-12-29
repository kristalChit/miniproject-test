package sg.edu.nus.iss.miniproject.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.model.Recipe;
import sg.edu.nus.iss.miniproject.model.User;
import sg.edu.nus.iss.miniproject.service.RecipeService;
import sg.edu.nus.iss.miniproject.service.UserService;

// UserController.java
@Controller
@RequestMapping("/users")
public class RecipeController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @PostMapping("/register")
    public String registerUser(@RequestBody MultiValueMap<String, String> form, Model model) {

        String email = form.getFirst("username").toLowerCase();

        if (!userService.existingUser(email)) {
            System.out.println("CREATING NEW USER");
            User user = new User();
            JsonObject username;
            user = User.create(username);
            userService.saveUser(u);

            model.addAttribute("username", username);
            return "search";
        }

        return "login";
    }

    @PostMapping(path = "/login")
    public String userLogin(@RequestBody MultiValueMap<String, String> form, Model model) {

        String username = form.getFirst("username").toLowerCase();
        User user;

        if (!userService.existingUser(username)) {
            return "register";
        }

        if (username.equals(user.getUsername())) {

            model.addAttribute("username", username);
            return "home";

        } else {

            return "register";
        }
    }

    @GetMapping("/showRecipes")
    public String getShowRecipes(@RequestParam("username") String username, Model model) {

        List<Recipe> recipes = recipeService.getShowRecipes();

        model.addAttribute("useranme", username);
        model.addAttribute("recipes", recipes);

        return "showRecipes";
    }

    @GetMapping("/searchRecipes")
    public String getSearchRecipes(@RequestParam("email") String userEmail, Model model) {

        Recipe recipe = recipeService.getsearchRecipes();

        model.addAttribute("username", username);
        model.addAttribute("recipes", recipes);

        return "searchRecipes";
    }

    @GetMapping("/RecipeInstrictions")
    public String getRecipeInstructions(@RequestParam("email") String userEmail, Model model) {

        Recipe recipe = recipeService.getrecipeInstructions();

        model.addAttribute("username", username);
        model.addAttribute("recipes", recipes);

        return "RecipeInsutrction";
    }

    @PostMapping("/favourites")
    public String getFavourites (@RequestBody MultiValueMap<String, String> form,
            @RequestParam("saveFavourites") List<String> favouritesValue, @RequestParam("username") String username,
            Model model) {

        System.out.println("Favorites: " + favouritesValue);

        if (favouritesValue.size() == 1) {

            userService.saveRecipes(username, favouritesValue);
        }

        List<String> savedList = new LinkedList<>();

        for (String selectedRecipe : favouritesValue) {

            savedList.add(selectedRecipe);
        }
        System.out.printf("SAVED LIST: %s\n\n", savedList);

        userService.saveRecipes(username, savedList);

        model.addAttribute("username", username);
        return "favourites";
    }

    @RequestMapping(path = "user")
    public String retrieveRecipes(@PathVariable(name = "username") String username, Model model) {

        if (userService.existingUser(username)) {
            List<String> saved = userService.favouriteRecipes(username);
            model.addAttribute("recipes", saved);
            return "favourites";
        } else {
            return "error";
        }

    }
}
