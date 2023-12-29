package sg.edu.nus.iss.miniproject.repositories;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class UserRepository{

    @Autowired
    @Qualifier("redis")

    public Optional<String> checkUser(String username) {

        if (redisTemplate.hasKey(username)) {

            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

            String result = valueOps.get(username);
            return Optional.of(result);
        }

        return Optional.empty();
    }

    public void saveUser(User u) {

        String username = u.getUsername();

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(username, u.toJson(u).toString());

    }

    public boolean userExists(String username) {

        if (redisTemplate.hasKey(username)) {
            return true;
        }
        return false;

    }

    public void savedRecipes(String username, List<String> s) {

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String userDetails = valueOps.get(username);

        Reader stringReader = new StringReader(userDetails);
        JsonReader jsonReader = Json.createReader(stringReader);
     
        JsonObject jo = jsonReader.readObject();
        User u = User.create(jo);

        List<String> existingRecipes;

        if (u.getRecipes() == null) {

            u.setRecipes(s);

        } else {

            existingRecipes = u.getRecipes();

            for (String singleRecipe : s) {
                if (!existingRecipes.contains(singleRecipe)) {
                    existingRecipes.add(singleRecipe);
                }
            }
            u.setRecipes(existingRecipes);
        }

        valueOps.set(username, u.toJson(u).toString());

    }

    public List<String> favourites(String username) {

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String userDetails = valueOps.get(username);

        Reader stringReader = new StringReader(userDetails);
        JsonReader jsonReader = Json.createReader(stringReader);
        // Read and save the payload as Json Object
        JsonObject jo = jsonReader.readObject();
        User u = User.create(jo);

        return u.getFavourites();

    }
}