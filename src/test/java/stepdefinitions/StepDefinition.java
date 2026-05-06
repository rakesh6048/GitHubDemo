package stepdefinitions;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;


import pojo.addPlace.AddPlace;
import pojo.addPlace.AddPlaceResponse;
import pojo.ecommerce.AddProductResponse;
import pojo.ecommerce.LoginResponse;
import pojo.ecommerce.OrdersResponse;
import resources.APIResources;
import resources.DemoResourceAsString;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class StepDefinition extends Utils {
	RequestSpecification req, getReq;
	ResponseSpecification resSpec;
	Response response;
	static String place_id;
	public static String token;
	static String user_id;
	static String productId;
	static String orderId;
	static String prodId;
	JsonPath jsp;
	TestDataBuild data = new TestDataBuild();
	//APIResources resourceAPI;
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
		
		req = given().spec(requestSpecification("Add_Place")).body(data.addPlacePayload(name, language, address));

	}

	@When("user call {string} with {string} http request")
	public void user_call_with_http_method_request(String resource, String method) {
	 	 //DemoResourceAsString resource= new DemoResourceAsString();
	 //System.out.println(resource.addPlaceAPI());
		//response= res.when().post(resourceAPI.getResource())
		//.then().log().all().spec(resSpec).extract().response();
	 
	 APIResources resourceAPI=APIResources.valueOf(resource);
	 System.out.println(resourceAPI.getResource());
	 
	 resSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

	 if(method.equalsIgnoreCase("POST")) {
		 response= req.when().post(resourceAPI.getResource());
		 System.out.println(response.asString());
	 }
	 else if(method.equalsIgnoreCase("GET")) {
		 response= req.when().get(resourceAPI.getResource());
		 System.out.println(response.asString());
	 }else if(method.equalsIgnoreCase("DELETE")) {
		 response= req.when().delete(resourceAPI.getResource());
		 System.out.println(response.asString());
	 }else if(method.equalsIgnoreCase("PUT")) {
		 response= req.when().put(resourceAPI.getResource());
		 System.out.println(response.asString());
	 }
	}

	@Then("the API call got success with status code {int}")
	public void the_API_ca_got_success_with_status_code(Integer int1) {
		if(int1==200) {
		assertEquals(response.getStatusCode(), 200);
		}else if(int1==201) {
			assertEquals(response.getStatusCode(), 201);
		}else if(int1==204) {
			assertEquals(response.getStatusCode(), 204);
		}
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is_ok(String keyValue, String Expectedvalue) {
		
		assertEquals(getJsonPath(response, keyValue), Expectedvalue);
	}
	
	@Then("verify palce_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException{
		
		AddPlaceResponse res =response.as(AddPlaceResponse.class);
		place_id =res.getPlace_id();
		//place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification("Add_Place")).queryParam("place_id", place_id);
		user_call_with_http_method_request(resource, "GET");
		
		//String actualName = getJsonPath(response, "name");
		//Assert.assertEquals(actualName, expectedName);
	}
	
	//When delete the place using "DeletePlaceAPI"
	/*@When("delete the place using {string}")
	public void verify_delete_place_to_using_DeletePlaceAPI(String resource) throws IOException{
		
		//place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification()).queryParam("place_id", place_id).body(data.deletePlacePayLoad(place_id));
		user_call_with_http_method_request(resource, "DELETE");
	}*/
	
	@When("update the address as {string} using {string}")
	public void verify_update_place_to_using_updatePlaceAPI(String address, String resource) throws IOException{
		
		//place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification("Add_Place")).queryParam("place_id", place_id).body(data.updatePlacePayLoad(place_id, address));
		user_call_with_http_method_request(resource, "PUT");
	}
	@Given("delete place payload")
	public void delete_place_payload() throws IOException {
		
		req = given().spec(requestSpecification("Add_Place")).queryParam("place_id", place_id).body(data.deletePlacePayLoad(place_id));
	}
	
	@Given("Ecommerce Add Product Payload with {string} {string}")
	public void ecommerce_Add_Product_Payload_with(String userName, String password) throws IOException {
		req = given().spec(requestSpecification("Ecomerce")).body(data.ecomLoginPayLoad(userName, password));
	}
	
	@Then("verify access_token created using {string}")
	public void verify_Access_Token_created_maps_to_using(String resource) throws IOException{
		
		LoginResponse loginRes = response.as(LoginResponse.class);
		token = loginRes.getToken();
		System.out.println("Access Tokens : "+token);
		user_id = loginRes.getUserId();
		System.out.println("user_id : "+user_id);
		
		
	}
	
	@Given("Ecommerce Add Product Payload with form data")
	public void ecommerce_Add_Product_Payload_with_form_data() throws IOException {
	    
	     req = given().log().all().spec(requestSpecifications("EcomerceMultipart")).param("productName", "Laptop").param("productAddedBy", user_id)
				   .param("productCategory", "fashion").param("productSubCategory", "shirts")
				   .param("productPrice", "11500").param("productDescription", "Lenevo")
				   .param("productFor", "women")
				   .multiPart("productImage", new File("D://API Automation//API Autmation Using Rest Assured//Section 9 , 10- Oauth 2.0//ClientCredential.png"));
		
		
	}
	@Then("verify {string} created from response body using {string}")
	public void verify_created_from_response_body_using(String Idss, String resource) throws IOException {
	    
		if(Idss.equals("productId")) {
		
		AddProductResponse prodRes= response.as(AddProductResponse.class);
		productId =prodRes.getProductId();
		String mesg = prodRes.getMessage();
		System.out.println("Message : "+mesg);
	    jsp = new JsonPath(response.asString());
		prodId= jsp.get("productId");
		System.out.println("product id :"+prodId);
		System.out.println("product id :"+prodRes);
		} else if(Idss.equals("orderId")) {
			 jsp = new JsonPath(response.asString());
			orderId =jsp.get("orders[0]");
			System.out.println("orderId : "+orderId);
			/*OrdersResponse orderRes= response.as(OrdersResponse.class);
			orderId = orderRes.getProductOrderId();
			System.out.println("orderId : "+orderId);*/
		} else if(Idss.equals("orderview")) {
			req = given().log().all().spec(requestSpecifications("EcomerceMultipart")).queryParam("id", orderId);
			user_call_with_http_method_request(resource, "GET");
			jsp = new JsonPath(response.asString());
			String productName = jsp.get("data.productName");
			String country = jsp.get("data.country");
			String productDescription = jsp.get("data.productDescription");
			System.out.println(productName);
			System.out.println(country);
			System.out.println(productDescription);
			
			Assert.assertEquals(productName, "Laptop");
			Assert.assertEquals(country, "India");
			Assert.assertEquals(productDescription, "Lenevo");
			
		}
		
		
	}
	@Given("Ecommerce create order Payload with request")
	public void ecommerce_create_order_Payload_with_request() throws IOException {
		
		req = given().log().all().spec(requestSpecificationsecom()).body(data.craeteOrderPayLoad(prodId));
		
	}
	@When("deleted the {string}")
	public void payload(String prodOder) throws IOException {
	    
		if(prodOder.equalsIgnoreCase("Product")) {
			
			req = given().log().all().spec(requestSpecificationsecom()).pathParam("prodId", prodId);
			
			//jsp = new JsonPath(response.asString());
			
			//String expectedMsg= jsp.get("message");
			
			//Assert.assertEquals(expectedMsg, "Product Deleted Successfully");
			
		}else if(prodOder.equalsIgnoreCase("Order")) {
			
			req = given().log().all().spec(requestSpecificationsecom()).pathParam("orderId", orderId);
			
            //jsp = new JsonPath(response.asString());
			
			//String expectedMsg= jsp.get("message");
			
			//Assert.assertEquals(expectedMsg, "Orders Deleted Successfully");
		}
		
	}


}
