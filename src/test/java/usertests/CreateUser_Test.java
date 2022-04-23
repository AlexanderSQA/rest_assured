package usertests;

import static services.user.UserApi.BASE_URL;

import com.github.javafaker.Faker;
import dto.user.CreateUserResponseBody;
import dto.user.GetUserResponseBody;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;


public class CreateUser_Test {
  UserApi userApi = new UserApi();
  Faker faker = new Faker();

  //Проверить, что при создании Юзера в поле message записывается id нового пользователя
  //Проверить, что при создании Юзера в поле type записывается значение "unknown"
  @Test
  public void checkCreateUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpec200());
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
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/CreateUser.json"))
        .log().all()
        .extract().as(CreateUserResponseBody.class);

    GetUserResponseBody userResponseBody = userApi.getUser(user.getUsername())
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/GetUser.json"))
        .log().all().extract().as(GetUserResponseBody.class);

    Assert.assertEquals(userResponseBody.getPhone(), user.getPhone());
    Assert.assertNotNull(userResponseBody.getEmail());
    Assert.assertNotNull(userResponseBody.getPhone());
    Assert.assertNotNull(user.getPassword());
  }
}

