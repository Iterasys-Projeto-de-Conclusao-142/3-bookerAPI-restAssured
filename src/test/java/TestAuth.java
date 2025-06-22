import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TestAuth extends Common {

    @Test
    public void testAuth() throws IOException {

        String jsonBody = new String(Files.readAllBytes(
            Paths.get("src/test/resources/json/userAuth.json")
        ));
        
        String token = given()
            .contentType(ct)
            .body(jsonBody)
        .when()
            .post(baseUrl + "/auth")
        .then()
            .statusCode(200)
            .body("token", notNullValue()) // Verifica se o token existe
            .extract()
            .path("token"); // Extrai o token da resposta
        
        System.out.println("Token recebido: " + token);
    }
}