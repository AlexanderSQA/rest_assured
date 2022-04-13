import dto.User;
import dto.UserResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.UserApi;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser_Test {
  UserApi userApi = new UserApi();

  @Test
  public void checkCreateUser() {
    User user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .id(0 + (int) (Math.random() * 100000))
        .userStatus(3)
        .email("example" + (0 + (int) (Math.random() * 100000)) + "@ya.ru")
        .phone("+79" + (0 + (int) (Math.random() * 999999)))
        .build();

    userApi.createUser(user)
        .then()
        .log().all()
        .statusCode(200)
        .body("type", equalTo("unknown"));

    Response response = userApi.createUser(user);
    String actualRes = response
        .jsonPath()
        .get("message");

    String type = response
        .then()
        .extract()
        .as(UserResponse.class)
        .getType();

    Assert.assertEquals(actualRes, Integer.toString(user.getId()));
    Assert.assertEquals(type, "unknown");

  }
}

