package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stepdefinitions.StepDefinition;

public class Utils {
	
	public static RequestSpecification req;
	public RequestSpecification requestSpecification(String reqType) throws IOException {
		
		if(req==null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		if(reqType.equalsIgnoreCase("Add_Place")) {
		req = new RequestSpecBuilder().setBaseUri(getGlobalData("baseUri")).addQueryParam("key", "qaclick")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		}else if(reqType.equalsIgnoreCase("Ecomerce")) {
			req = new RequestSpecBuilder().setBaseUri(getGlobalData("baseUriEcom"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
		}
		return req;
		}
		return req;
	}
	
 public RequestSpecification requestSpecifications(String reqType) throws IOException {
		
		
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 if(reqType.equalsIgnoreCase("EcomerceMultipart")) {
			req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.addHeader("authorization", StepDefinition.token).build();
		}
		return req;
		
	}
 
 public RequestSpecification requestSpecificationsecom() throws IOException {
		
		
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 
			req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.addHeader("authorization", StepDefinition.token).setContentType(ContentType.JSON).build();
		
		return req;
		
	}
	
	public static String getGlobalData(String key) throws IOException {
		
		Properties prop = new Properties();
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalData.properties");
		
		prop.load(fis);
		
		return prop.getProperty(key);
		
	}
	
	public String getJsonPath(Response response, String key) {
		
		
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		System.out.println(js.get(key).toString());
		return js.get(key).toString();
		
	}

}
