package services.user;

import static io.restassured.RestAssured.given;

import dto.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Locale;


public class UserApi {
  public static final String BASE_URL = System.getProperty("base_url").toLowerCase(Locale.ROOT);
  private final RequestSpecification spec;

  @Autowired
  public UserApi() {
    spec = given()
        .baseUri(BASE_URL)
        .basePath("/user")
        .contentType(ContentType.JSON);
  }

  public ValidatableResponse createUser(User user) {
    return given(spec)
        .log().all()
        .body(user)
        .when()
        .post()
        .then();
  }

  public ValidatableResponse getUser(String username) {
    return given(spec)
        .log().all()
        .when()
        .get("/" + username)
        .then();
  }

  public Response getUserWithAuth(String username, String password) {
    return given(spec)
        .auth().basic(username, password)
        .log().all()
        .when()
        .get("/login");
  }

  public ValidatableResponse deleteUser(String username) {
    return given(spec)
        .log().all()
        .when()
        .delete("/" + username)
        .then();
  }
}
