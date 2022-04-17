import dto.CreateUserResponseBody;
import dto.GetUserResponseBody;
import dto.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.CreateUserUtil;
import services.UserApi;

import static org.hamcrest.Matchers.*;

public class User_Test {
  UserApi userApi = new UserApi();

  @Test //TODO дописать кейсы
  public void checkCreateUser() {
    User user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .username("Ivan1234")
        .password("123")
        .id((int) (Math.random() * 100000))
        .userStatus(3)
        .email("example" + ((int) (Math.random() * 100000)) + "@ya.ru")
        .phone("+79" + ((int) (Math.random() * 999999999)))
        .build();

    userApi.createUser(user)
        .then()
        .log().all()
        .statusCode(200)
        .body("type", equalTo("unknown"))
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

    Response response = userApi.createUser(user);
    String actualRes = response.jsonPath().get("message");

    String type = response.then().extract().as(CreateUserResponseBody.class).getType();

    Assert.assertEquals(actualRes, Integer.toString(user.getId()));
    Assert.assertEquals(type, "unknown");
  }

  @Test //TODO дописать кейсы
  public void checkGetUser() {
    User user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .username("Ivan1")
        .password("123")
        .id((int) (Math.random() * 100000))
        .userStatus(3)
        .email("example" + ((int) (Math.random() * 100000)) + "@ya.ru")
        .phone("+79" + ((int) (Math.random() * 999999999)))
        .build();

    userApi.createUser(user);
    userApi.getUser(user.getUsername())
        .then()
        .log().all()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/GetUser.json"));

    Response response = userApi.getUser(user.getUsername());
    String phone = response.then().extract().as(GetUserResponseBody.class).getPhone();
    //TODO дописать еще 1 проверку

    Assert.assertEquals(phone, user.getPhone());
  }
}

