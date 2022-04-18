package userTests;

import dto.user.GetUserLoginResponse;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;

import static services.user.UserApi.BASE_URL;

public class GetUserLogin_Test {
  UserApi userApi = new UserApi();

  //Проверить статус код 200 при корректном логировании
  //Проверить тип "unknown" при корректном логировании
  //Проверить Начало стринги "logged in user session:" при корректном логировании
  @Test
  public void checkLoginUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec200());
    User user = User.builder()
        .username("Ivan1234")
        .password("1234")
        .build();

    GetUserLoginResponse userLoginResponse = userApi.getUserWithAuth(user.getUsername(), user.getPassword())
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/GetUserLogin.json"))
        .log().all()
        .extract().as(GetUserLoginResponse.class);

    Assert.assertEquals(userLoginResponse.getCode(), 200);
    Assert.assertEquals(userLoginResponse.getType(), "unknown");
    Assert.assertTrue(userLoginResponse.getMessage().startsWith("logged in user session:"));
  }
}
