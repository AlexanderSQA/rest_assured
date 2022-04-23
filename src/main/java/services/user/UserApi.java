package services.user;

import static io.restassured.RestAssured.given;

import dto.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class UserApi {
  //  public static final String BASE_URL = System.getProperty("base_url").toLowerCase(Locale.ROOT);
  public static final String BASE_URL = "https://petstore.swagger.io/v2";
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
        .post();
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

  public Response deleteUser(String username) {
    return given(spec)
        .log().all()
        .when()
        .delete("/" + username);
  }
}
