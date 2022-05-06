package usertests;

import static org.hamcrest.Matchers.equalTo;

import com.github.javafaker.Faker;
import dto.user.CreateUserResponseBody;
import dto.user.DeleteUserResponseBody;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.ApiModule;
import services.user.UserApi;


public class DeleteUser_Test {
  ApplicationContext context = new AnnotationConfigApplicationContext(ApiModule.class);
  private UserApi userApi = context.getBean(UserApi.class);
  Faker faker = new Faker();

  //Проверить, что домен почты у пользователя "@ya.ru"
  //Проверить, что телефон у клиента начинается с "+79"
  //Проверить, что пароль не пустой
  @Test
  public void checkDeleteUser() {

    User user = User.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .username(faker.name().username())
        .password(faker.internet().password())
        .id(faker.number().numberBetween(1, 10000000))
        .userStatus(faker.number().numberBetween(0, 10))
        .email(faker.internet().emailAddress())
        .phone(faker.phoneNumber().cellPhone())
        .build();


    userApi.createUser(user)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/CreateUser.json"))
        .log().all()
        .extract().as(CreateUserResponseBody.class);

    DeleteUserResponseBody deleteUserResponseBody = userApi.deleteUser(user.getUsername())
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/DeleteUser.json"))
        .log().all().extract().as(DeleteUserResponseBody.class);

    userApi.getUser(user.getUsername())
        .body("message", equalTo("User not found"))
        .assertThat().statusCode(404)
        .log().all();

    Assert.assertEquals(deleteUserResponseBody.getMessage(), user.getUsername());

  }
}
