package stepDefinition;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
import cucumber.api.PendingException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class products  {

    public int StatusCode;
    public RequestSpecification httpRequest;
    public Response response;
    public int ResponseCode;
    public ResponseBody body;
    public JSONObject requestParams;
    public JsonPath jsnpath;
    public String ObjValue;

//    static ExtentReports report;
//    public static ExtentTest test;
//    public static ExtentReports extent = new ExtentReports();


    @Given("I hit the url to get the products api endpoint")
    public void I_hit_the_url_to_get_the_products_api_endpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";


    }

    @When("I pass the url in the request")
    public void i_pass_the_url_in_the_request() {
        httpRequest = given();
        response = httpRequest.get("Products");

    }

    @Then("^I receive the response code as (\\d+)$")
    public void i_receive_the_response_code_as(Integer int1) {
        ResponseCode = response.getStatusCode();
        System.out.println("test console log and status code is: " + ResponseCode);
        Assert.assertEquals(ResponseCode, 200);
    }

    @Then("^I verify that the rate of the first product is \"([^\"]*)\"$")
    public void i_verify_that_the_rate_of_the_first_product_is(String rate) {
        body = response.getBody();
//convert body to string
        String responseBody = body.asString();
//JSON Representation from Response body
        JsonPath jsonpath = response.jsonPath();
        String s = jsonpath.getJsonObject("rating[0].rate").toString();
        System.out.println("Response body: " + responseBody + "\n" + "object for validation: " + s);
        Assert.assertEquals(s, rate);

    }


    @Given("I hit the url to post the products api endpoint")
    public void iHitTheUrlToPostTheProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        httpRequest = given();
        requestParams = new JSONObject();


    }


    @And("^I pass the request body of product title \"([^\"]*)\"$")
    public void iPassTheRequestBodyOfProductTitle(String title) {

        requestParams.put("title", title);
        requestParams.put("price", "10");
        requestParams.put("description", "baduy");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("category", "action");

        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("Products");
        ResponseBody body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        JsonPath jsonpath = response.jsonPath();
        ObjValue = jsonpath.getJsonObject("id").toString();


    }


    @Then("^I receive the response body with as a \"([^\"]*)\"$")
    public void iReceiveTheResponseBodyWithAsA(String ID) {
        Assert.assertEquals(ID, ObjValue);

    }

    @Given("I hit the url of put product api endpoint")
    public void iHitTheUrlOfPutProductApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("^I pass the url of products in the request with \"([^\"]*)\"$")
    public void iPassTheUrlOfProductsInTheRequestWith(String productnumber) {
        httpRequest = RestAssured.given();
        requestParams.put("title", "test product");
        requestParams.put("price", "13.5");
        requestParams.put("description", "lorem ipsum set");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("cateogry", "electronic");
        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.put("products/" + productnumber);
        ResponseBody body = response.getBody();
        JsonPath jsnpath = response.jsonPath();
        ObjValue = jsnpath.getJsonObject("id").toString();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());



    }

    @Given("I hit the url of delete product api endpoint")
    public void iHitTheUrlOfDeleteProductApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("^I pass the url of delete products in the request with \"([^\"]*)\"$")
    public void iPassTheUrlOfDeleteProductsInTheRequestWith(String productnumber) {
        httpRequest = RestAssured.given();
        requestParams.put("title", "test product");
        requestParams.put("price", "13.5");
        requestParams.put("description", "lorem ipsum set");
        requestParams.put("image", "https://i.pravatar.cc");
        requestParams.put("cateogry", "electronic");
        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.delete("products/" + productnumber);
        ResponseBody body = response.getBody();
        JsonPath jsnpath = response.jsonPath();
        ObjValue = jsnpath.getJsonObject("id").toString();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }



}
