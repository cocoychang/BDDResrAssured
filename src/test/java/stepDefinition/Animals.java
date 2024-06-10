package stepDefinition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class Animals {
    public RequestSpecification httpRequest;
    public Response response;

    public ResponseBody body;

    String types;

    String accessToken;

    int actualStatusCode;

    public String unauthorizedtitle;

    @Given("i am an authenticated user")
    public void iAmAnAuthenticatedUser() {
        RestAssured.baseURI = "https://api.petfinder.com/v2/";
        response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type","client_credentials")
                .formParam("client_id","2RU9OFAhDlFJTsU2vTJBoxSSvTwTUs5iCpbHco3rV9HVwhJe5O")
                .formParam("client_secret", "ubgaqZgisGjZFybnPn328Khd7MaGbJ5yKBH7eXb4")
                .when()
                .post(RestAssured.baseURI + "oauth2/token");

        body = response.getBody();
        String responseBody = body.asString();
        System.out.println(responseBody);
        JsonPath jsonpath = response.jsonPath();
        accessToken = jsonpath.getJsonObject("access_token").toString();

               
    }

    @When("i hit the get animals api url")
    public void iHitTheGetAnimalsApiUrl() {
        response = RestAssured.given()
                .header("Authorization","Bearer "+ accessToken)
                .when()
                .get(RestAssured.baseURI + "types");
        JsonPath jsonpath = response.jsonPath();
        types = jsonpath.getJsonObject("types.name[0]").toString();
        System.out.println(types);
    }


    @Then("^i get (\\d+) as the response code$")
    public void iGetAsTheResponseCode(int arg0) {
        actualStatusCode = response.getStatusCode();
        System.out.println("Status code: "+ actualStatusCode);
        Assert.assertEquals(arg0,actualStatusCode);
    }


    @Then("^i get animals in the response body of the api validate the value of \"([^\"]*)\"$")
    public void iGetAnimalsInTheResponseBodyOfTheApiValidateTheValueOf(String Animal) {
        Assert.assertEquals(Animal,types);
    }

    @Given("i am an unauthenticated user")
    public void iAmAnUnauthenticatedUser() {

        RestAssured.baseURI = "https://api.petfinder.com/v2/";
        response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type","client_credentials")
                .formParam("client_id","")
                .formParam("client_secret", "")
                .when()
                .post(RestAssured.baseURI + "oauth2/token");
        JsonPath jsonpath = response.jsonPath();
        unauthorizedtitle = jsonpath.getJsonObject("title").toString();
        System.out.println(unauthorizedtitle);
        Assert.assertEquals(unauthorizedtitle, "invalid_client");
        actualStatusCode = response.getStatusCode();
        System.out.println(actualStatusCode);
    }
}
