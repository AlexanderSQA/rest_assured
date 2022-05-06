package usertests;

import com.github.javafaker.Faker;
import dto.user.GetUserLoginResponse;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.ApiModule;
import services.user.UserApi;


public class GetUserLogin_Test {
  ApplicationContext context = new AnnotationConfigApplicationContext(ApiModule.class);
  private UserApi userApi = context.getBean(UserApi.class);
  Faker faker = new Faker();
  //Проверить статус код 200 при корректном логировании
  //Проверить тип "unknown" при корректном логировании
  //Проверить Начало стринги "logged in user session:" при корректном логировании
  @Test
  public void checkLoginUser() {

    User user = User.builder()
        .username(faker.name().username())
        .password(faker.internet().password())
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
