package usertests;

import dto.user.CreateUserResponseBody;
import dto.user.GetUserResponseBody;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;

import static services.user.UserApi.BASE_URL;

public class CreateUser_Test {
  UserApi userApi = new UserApi();

  //Проверить, что при создании Юзера в поле message записывается id нового пользователя
  //Проверить, что при создании Юзера в поле type записывается значение "unknown"
  @Test
  public void checkCreateUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec200());
    User user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .username("Ivan" + (int) (Math.random() * 100000))
        .password("123")
        .id((int) (Math.random() * 100000))
        .userStatus((int) (Math.random() * 10))
        .email("example" + ((int) (Math.random() * 100000)) + "@ya.ru")
        .phone("+79" + ((int) (Math.random() * 999999999)))
        .build();

    userApi.createUser(user)
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/CreateUser.json"))
        .log().all()
        .extract().as(CreateUserResponseBody.class);

    GetUserResponseBody userResponseBody = userApi.getUser(user.getUsername())
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/GetUser.json"))
        .log().all().extract().as(GetUserResponseBody.class);

    Assert.assertEquals(userResponseBody.getPhone(), user.getPhone());
    Assert.assertTrue(userResponseBody.getEmail().endsWith("@ya.ru"));
    Assert.assertTrue(userResponseBody.getPhone().startsWith("+79"));
    Assert.assertNotNull(user.getPassword());
  }
}

