package sg.edu.nus.iss.miniproject.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.miniproject.service.RecipeService;
import sg.edu.nus.iss.miniproject.service.UserService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(path = "/{username}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getList(@PathVariable(name = "username") String username) {

        if (!UserService.existingUser(username)) {
            JsonObject errorMsg = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();

            String text = errorMsg.toString();

            return ResponseEntity
                    .badRequest()
                    .body(text);
        }

        List<String> savedRecipes = UserService.favourites (username);

        if (savedRecipes == null) {

            JsonObject errorMsg = Json.createObjectBuilder()
                    .add("error", "User does not have any favourites")
                    .build();

            String text = errorMsg.toString();

            return ResponseEntity
                    .badRequest()
                    .body(text);
        }

        List<String> list = new LinkedList<>();
        int i = 1;

        for (String recipe : savedRecipess) {
            if (!recipe.equals("")) {
                JsonObject builder = Json.createObjectBuilder()
                        .add("Recipe " + i, recipe)
                        .build();
                i++;
                String jsonObject = builder.toString();
                list.add(jsonObject);
            }

        }

        return ResponseEntity.ok(list.toString());
    }

}