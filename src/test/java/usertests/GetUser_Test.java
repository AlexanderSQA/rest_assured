package usertests;

import dto.user.GetUserResponseBody;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;

import static services.user.UserApi.BASE_URL;

public class GetUser_Test {
  UserApi userApi = new UserApi();
  //Проверить, что домен почты у пользователя "@ya.ru"
  //Проверить, что телефон у клиента начинается с "+79"
  //Проверить, что пароль не пустой
  @Test
  public void checkGetUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec200());
    User user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .username("Ivan1234")
        .password("1234")
        .id(44863)
        .userStatus(3)
        .email("example95046@ya.ru")
        .phone("+79301543521")
        .build();


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
