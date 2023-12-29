package sg.edu.nus.iss.miniproject.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.Repository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.miniproject.model.RecipeInstructions;
import sg.edu.nus.iss.miniproject.repositories.Recipe;

@Service
public class RecipeService {

    @Value("${api.key}")
    private String apiKey;

    public ArrayList<Recipe> getRecipes(Recipe recipe) throws IOException {
        ArrayList<Recipe> listOfRecipes = new ArrayList<>();
        
        final String url = "www.themealdb.com/api/json/v1/1/filter.php?i=";
        
        String payload;

        String uri = UriComponentsBuilder.fromUriString(url)
                .path("/recipeList")
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("data");

        for(JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String Meal = obj.getString("strMeal", "");
            String MealThumb = obj.getString("strMealThumb", "");
            String Id = obj.getString("idMeal", "");
            recipe.add(new Recipe(Meal, MealThumb, Id));
        }

        return listOfRecipes;
    }

    public ArrayList<RecipeInstructions> getRecipeInstructionsById(Recipe recipe) throws IOException {
        ArrayList<RecipeInstructions> recipeInstructionById = new ArrayList<>();

        String payload;

        String recipeInstructionsByIdUrl = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + recipe.getRecipeId();

        String uri = UriComponentsBuilder.fromUriString(recipeInstructionsByIdUrl)
                .path("/recipeSteps")
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(uri).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("data");

        for(JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String Id = obj.getString("idMeal", "");
            String Meal = obj.getString("strMeal", "");
            String Category = obj.getString("strCategory", "");
            String Cuisine = obj.getString("strArea", "");
            String Instruction = obj.getString("strInstructions", "");
            String MealThumb = obj.getString("strMealThumb", "");
            String Youtube = obj.getString("strYoutube", "");
            String Ingredient = obj.getString("strIngredient", "");
            String Measure = obj.getString("strMeasure", "");
            recipeInstructionById.add(new RecipeInstructions(Id, Meal, Category, Cuisine, Instruction, MealThumb, Youtube, Ingredient, Measure));
        }

        return recipeInstructionById;
    }

    public ArrayList<RecipeInstructions> getRecipeInstructionsByName(Recipe recipe, ArrayList<RecipeInstructions> recipeInstructionByName) throws IOException {
        ArrayList<RecipeInstructions> recipeInstructionsList = new ArrayList<>();

        String payload;

        String recipeInstructionsByNameUrl = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + recipe.getRecipeId();

        String uri = UriComponentsBuilder.fromUriString(recipeInstructionsByNameUrl)
                .path("/recipeSteps")
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(uri).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray array = reader.readObject().getJsonArray("data");

        for(JsonValue value : array) {
            JsonObject obj = value.asJsonObject();
            String Id = obj.getString("idMeal", "");
            String Meal = obj.getString("strMeal", "");
            String Category = obj.getString("strCategory", "");
            String Cuisine = obj.getString("strArea", "");
            String Instruction = obj.getString("strInstructions", "");
            String MealThumb = obj.getString("strMealThumb", "");
            String Youtube = obj.getString("strYoutube", "");
            String Ingredient = obj.getString("strIngredient", "");
            String Measure = obj.getString("strMeasure", "");
            recipeInstructionsList.add(new RecipeInstructions(Id, Meal, Category, Cuisine, Instruction, MealThumb, Youtube, Ingredient, Measure));
        }

        return recipeInstructionByName;
    }
    }
