package sg.edu.nus.iss.miniproject.model;

import jakarta.json.JsonObject;

public class Recipe {
    private String Id; 
    private String Meal;      
    private String Category;       
    private String Cuisine;       
    private String Instruction;  
    private String MealThumb; 
    private String Youtube;   
    private String Ingredient;
    private String Measure;
   
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMeal() {
        return Meal;
    }

    public void setMeal(String meal) {
        Meal = meal;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getMealThumb() {
        return MealThumb;
    }

    public void setMealThumb(String mealThumb) {
        MealThumb = mealThumb;
    }

    public String getYoutube() {
        return Youtube;
    }

    public void setYoutube(String youtube) {
        Youtube = youtube;
    }

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }

    public static Recipe Create(JsonObject jo) {
        
        Recipe recipe = new Recipe();
        recipe.setId(jo.getString("idMeal"));
        recipe.setMeal(jo.getString("strMeal"));
        recipe.setCategory(jo.getString("strCategory"));
        recipe.setCuisine(jo.getString("strArea"));
        recipe.setInstruction(jo.getString("strInstructions"));
        recipe.setMealThumb(jo.getString("strMealThumb\""));
        recipe.setYoutube(jo.getString("strYoutube"));
        recipe.setIngredient(jo.getString("strIngredient"));
        recipe.setIngredient(jo.getString("strMeasure"));

        return recipe;
    }
}