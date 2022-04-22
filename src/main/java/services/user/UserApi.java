package services.user;

import dto.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserApi {
//  public static final String BASE_URL = System.getProperty("base_url").toLowerCase(Locale.ROOT);
  public static final String BASE_URL = "https://petstore.swagger.io/v2";
  public static String userPath = "/user";

  private final RequestSpecification spec;

  public UserApi() {
    spec = given()
        .baseUri(BASE_URL)
        .basePath("/user")
        .contentType(ContentType.JSON);
  }

  public Response createUser(User user) {
    return given(spec)
        .log().all()
        .body(user)
        .when()
        .post(userPath);
  }

  public Response getUser(String username) {
    return given(spec)
        .log().all()
        .when()
        .get("/" + username);
  }

  public Response getUserWithAuth(String username, String password) {
    return given(spec)
        .auth().basic(username, password)
        .log().all()
        .when()
        .get("/login");
  }
}
