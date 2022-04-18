package services.user;

import dto.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth;

public class UserApi {
  public static final String BASE_URL = "https://petstore.swagger.io/v2";
  public static String CREATE_USER_PATH = "/user";
  public static String GET_USER_PATH = "/user/";
  public static String GET_USER_LOGIN_PATH = "/user/login";
  private final RequestSpecification spec;

  public UserApi() {
    spec = given()
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON);
  }

  public Response createUser(User user) {
    return given(spec)
        .log().all()
        .body(user)
        .when()
        .post(CREATE_USER_PATH);
  }

  public Response getUser(String username) {
    return given(spec)
        .log().all()
        .when()
        .get(GET_USER_PATH + username);
  }

  public Response getUserWithAuth(String username, String password) {
    return given(spec)
        .auth().basic(username, password)
        .log().all()
        .when()
        .get(GET_USER_LOGIN_PATH);
  }
}
