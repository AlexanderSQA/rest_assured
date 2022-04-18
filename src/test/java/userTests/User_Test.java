package userTests;

import dto.user.CreateUserResponseBody;
import dto.user.GetUserResponseBody;
import dto.user.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;

import static services.user.UserApi.BASE_URL;

public class User_Test {
  UserApi userApi = new UserApi();

  @Test //TODO дописать кейсы
  public void checkCreateUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecSchemaPlus200("schema/user/CreateUser.json"));
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


    Response response = userApi.createUser(user);
    String actualRes = response.jsonPath().get("message");

    String type = response.then().extract().as(CreateUserResponseBody.class).getType();

    Assert.assertEquals(actualRes, Integer.toString(user.getId()));
    Assert.assertEquals(type, "unknown");
  }

  //Проверить, что домен почты у пользователя "@ya.ru"
  //Проверить, что телефон у клиента начинается с "+79"
  @Test
  public void checkGetUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecSchemaPlus200("schema/user/GetUser.json"));
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

    userApi.createUser(user);
    userApi.getUser(user.getUsername())
        .then()
        .log().all();


    Response response = userApi.getUser(user.getUsername());
    String phone = response.then().extract().as(GetUserResponseBody.class).getPhone();


    Assert.assertEquals(phone, user.getPhone());
    Assert.assertTrue(user.getEmail().endsWith("@ya.ru"));
    Assert.assertTrue(user.getPhone().startsWith("+79"));
  }
}

