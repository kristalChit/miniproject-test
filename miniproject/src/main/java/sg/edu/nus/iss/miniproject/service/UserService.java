package sg.edu.nus.iss.miniproject.service;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.miniproject.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User checkUser(String username) {

        Optional<String> opt = User.get(username);
        if (opt.isEmpty())
            return null;

        String value = opt.get();
        Reader stringReader = new StringReader(value);
        JsonReader jsonReader = Json.createReader(stringReader);
        JsonObject jObject = jsonReader.readObject();

        return User.create(jObject);
    }

    public void saveUser(User user) {

        User.saveUser(user);
    }

    public static boolean existingUser(String username) {

        return User.userExists(username);
    }

    public void favourites(String username, List<String> s) {

        userRepo.favourites(username, s);

    }
}