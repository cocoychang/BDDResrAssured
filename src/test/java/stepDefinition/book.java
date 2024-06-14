package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class book {
    public RequestSpecification httpRequest;
    public Response response;
    public ResponseBody body;

    public JsonPath jsonPath;

    public JSONObject requestParams;

     String accessToken;

    public static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Given("I hit the url to get the list endpoint")
    public void i_hit_the_url_to_get_the_list_endpoint() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
    }


    @When("^I pass the url in the request with type \"([^\"]*)\"$")
    public void iPassTheUrlInTheRequestWithType(String type) {
        httpRequest = given();
        response = httpRequest.get("/books?type=" + type);
    }

    @Then("^Received the response body with list of books and (\\d+) status code$")
    public void receivedTheResponseBodyWithListOfBooksAndStatusCode(int statusCode) {
        body = response.getBody();
        String ResponseBody = body.asString();
        jsonPath = response.jsonPath();
        int responseCode = response.statusCode();
        Assert.assertEquals(statusCode, responseCode);
        System.out.println("Response body: " + ResponseBody + "\n" + "Status code: " + responseCode);
    }


    @When("^I pass the url in the request with bookId of \"([^\"]*)\"$")
    public void iPassTheUrlInTheRequestWithBookIdOf(String bookID) {
        httpRequest = given();
        response = httpRequest.get("/books/" + bookID);
    }

    @Then("^Received the response body with single book has current stock and (\\d+) status code$")
    public void receivedTheResponseBodyWithSingleBookHasCurrentStockAndStatusCode(int statusCode) {
        body = response.getBody();
        String ResponseBody = body.asString();
        jsonPath = response.jsonPath();
        int responseCode = response.statusCode();
        Assert.assertEquals(statusCode, responseCode);
        int current_stock = jsonPath.getJsonObject("current-stock");
        System.out.println("Response Body: " + ResponseBody + "\n" + "Status code: " + statusCode + "\n" + "Current-stock: " + current_stock);
        Assert.assertNotEquals(0, current_stock);
    }

    @When("I pass the url the api authentication endpoint")
    public void iPassTheUrlTheApiAuthenticationEndpoint() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me";
    }

    @And("^I pass the response body with client name \"([^\"]*)\" and email address \"([^\"]*)\"$")
    public void iPassTheResponseBodyWithClientNameAndEmailAddress(String client_name, String email_address) {
        requestParams = new JSONObject();
        requestParams.put("clientName", client_name);
        requestParams.put("clientEmail", email_address);
        //response = httpRequest.post("/api-clients/");
        response = RestAssured.given().contentType("application/json").body(requestParams).when().post(RestAssured.baseURI + "/api-clients/");
        //httpRequest.body(requestParams.toJSONString());
        body = response.getBody();
        //String responseBody = body.asString();
        //System.out.println(responseBody);
        System.out.println(response.getStatusLine());

    }



    @Then("^Received the response body with authenticationToken and (\\d+) status code$")
    public void receivedTheReponseBodyWithAuthenticationTokenAndStatusCode(int statusCode) {
        body = response.getBody();
        String responseBody = body.asString();
        int status_code = response.statusCode();
        jsonPath = response.jsonPath();
        accessToken = jsonPath.getJsonObject("accessToken").toString();
        Assert.assertEquals(statusCode, status_code);
        System.out.println("Response Body: " + responseBody + "\n" + "Status code; " + status_code);
    }



    @Given("that user has authentication")
    public void thatUserHasAuthentication() {
        try {
            Thread.sleep(2000);
            RestAssured.baseURI = "https://simple-books-api.glitch.me";
            requestParams = new JSONObject();
            requestParams.put("clientName", "coco14"+getTime());
            requestParams.put("clientEmail",getTime() +"coco14@gmail.com");
            response = RestAssured.given()
                    .contentType("application/json")
                    .body(requestParams).when()
                    .post(RestAssured.baseURI + "/api-clients/");
            body = response.getBody();
            String responseBody = body.asString();
            int status_code = response.statusCode();
            jsonPath = response.jsonPath();
            accessToken = jsonPath.getJsonObject("accessToken").toString();
            System.out.println("Response Body: " + responseBody + "\n" + "Status code; " + status_code);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @When("^Trigger endpoint with auth token and for orders request body with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void triggerEndpointWithAuthTokenAndForOrdersRequestBodyWithAnd(int bookId, String customer_name) {
        requestParams = new JSONObject();
        requestParams.put("bookId", bookId);
        requestParams.put("customerName", customer_name);
        response =  RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .body(requestParams)
                .post(RestAssured.baseURI + "/orders");
    }

    @Then("^Received the response body with successfully created order and (\\d+) status code$")
    public void receivedTheResponseBodyWithSuccessfullyCreatedOrderAndStatusCode(int statusCode) {
        body = response.getBody();
        String responseBody = body.asString();
        int status_code = response.statusCode();
        Assert.assertEquals(statusCode, status_code);
        System.out.println("Response Body: " + responseBody + "\n" + "Status code; " + status_code);
    }


    @When("Trigger the url with endpoint of get orders")
    public void triggerTheUrlWithEndpointOfGetOrders() {
        response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(RestAssured.baseURI+"/orders");
    }




    @Then("^Received response with all orders and (\\d+) status code$")
    public void receivedReponseWithAllOrdersAndStatusCode(int statusCode) {
        body = response.getBody();
        String responseBody = body.asString();
        int actual_status_code = response.statusCode();
        System.out.println("Response body :"+responseBody+"\n"+"status code: "+ actual_status_code);
        Assert.assertEquals(statusCode,actual_status_code);

    }
}
