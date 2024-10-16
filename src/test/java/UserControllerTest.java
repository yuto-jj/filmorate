import adapters.LocalDateAdapter;
import application.FilmApplication;
import com.google.gson.*;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {

    static final String URL = "http://localhost:8083/users/";
    final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @BeforeEach
    void beforeEach() throws InterruptedException {
        FilmApplication.main(new String[]{});
        Thread.sleep(2000);
    }

    @AfterEach
    void afterEach() {
        FilmApplication.stop();
    }

    @Test
    void postUser() throws IOException, InterruptedException {
        User user = User.builder()
                .id(2L)
                .name("John")
                .email("john@gmail.com")
                .login("john21")
                .birthday(LocalDate.of(2000, 2, 17))
                .build();

        String userJson = gson.toJson(user);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(URL);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(userJson))
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        JsonElement jsonElem = JsonParser.parseString(response.body());
        assertTrue(jsonElem.isJsonObject());
        JsonObject jsonObj = jsonElem.getAsJsonObject();
        User user1 = gson.fromJson(jsonObj, User.class);

        assertEquals(user.getId(), user1.getId());
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getLogin(), user1.getLogin());
        assertEquals(user.getBirthday(), user1.getBirthday());
    }
}


