package services.user;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {
  public static RequestSpecification requestSpec(String url) {
    return new RequestSpecBuilder()
        .setBaseUri(url)
        .setContentType(ContentType.JSON)
        .build();
  }

  public static ResponseSpecification responseSpecSchema(String schemaPath) {
    return new ResponseSpecBuilder()
        .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath))
        .build();
  }

  public static ResponseSpecification responseSpecSchemaPlus200(String schemaPath) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath))
        .build();
  }

  public static ResponseSpecification responseSpecSchemaPlus400(String schemaPath) {
    return new ResponseSpecBuilder()
        .expectStatusCode(400)
        .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath))
        .build();
  }

  public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
    RestAssured.requestSpecification = request;
    RestAssured.responseSpecification = response;
  }
}
