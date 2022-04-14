package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class UserApi {
  public static String BASE_URL = "https://petstore.swagger.io/v2";
  public static String PATH = "/user";
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
        .post(PATH);
  }
}