import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TestAuth extends Common {

    public static String token;

    // Método utilitário para gerar e retornar o token
    public static String gerarToken() {
        try {
            String jsonBody = new String(Files.readAllBytes(
                Paths.get("src/test/resources/json/userAuth.json")
            ));

            token = given()
                .contentType(ct)
                .body(jsonBody)
            .when()
                .post(baseUrl + "/auth")
            .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .path("token");

            System.out.println("Token gerado: " + token);
            return token;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    @Test
    public void testAuth() {
        gerarToken();
    }
}