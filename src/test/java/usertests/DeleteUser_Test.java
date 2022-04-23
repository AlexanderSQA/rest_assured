package usertests;

import static org.hamcrest.Matchers.equalTo;
import static services.user.UserApi.BASE_URL;

import com.github.javafaker.Faker;
import dto.user.CreateUserResponseBody;
import dto.user.DeleteUserResponseBody;
import dto.user.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.user.Specifications;
import services.user.UserApi;


public class DeleteUser_Test {
  UserApi userApi = new UserApi();
  Faker faker = new Faker();

  //Проверить, что домен почты у пользователя "@ya.ru"
  //Проверить, что телефон у клиента начинается с "+79"
  //Проверить, что пароль не пустой
  @Test
  public void checkGetUser() {
    Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecUnique(404));
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

    DeleteUserResponseBody deleteUserResponseBody = userApi.deleteUser(user.getUsername())
        .then()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/user/DeleteUser.json"))
        .log().all().extract().as(DeleteUserResponseBody.class);

    ValidatableResponse response = userApi.getUser(user.getUsername())
        .then()
        .body("message", equalTo("User not found"))
        .log().all();

    Assert.assertEquals(deleteUserResponseBody.getMessage(), user.getUsername());

    //TODO дописать ассерты на Гет удаленного Юзера (схема ответа отличается. как быть?)
    //TODO решить проблему со статус-кодами в методах. Причина падения.

  }
}
