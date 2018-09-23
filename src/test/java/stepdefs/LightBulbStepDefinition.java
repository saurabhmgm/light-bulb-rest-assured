package stepdefs;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Steps;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class LightBulbStepDefinition {

	@Steps
	LightBulb Result;
	@Steps
	StepReport report;

	private Response response;
	private int json;
	private static RequestSpecification request;

	private static String ENDPOINT_URI = "https://qa-challenges-lightbulb.atlassian.io";
	private static String ENDPOINT_ON_STATE =  "/api/allmethods/on";
	private static String ENDPOINT_OFF_STATE = "/api/allmethods/off";
	
	private String state;
	private LightBulb result;

	@Before
	public static void initSpec(){
		request = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri(ENDPOINT_URI)
				.build();
	}

	
	@Given("^a user wants to operate the light bulb$")
	public void aUserWantsToOperateTheLightBulb() throws Throwable {
		report.user_wants_to_operate_the_bulb();
	}



	@When("^the user switches (.*) the bulb$")
	public void theUserSwitchesStateTheBulb(String state) throws Throwable {
		this.state=state;
		if(state.equals("Off"))
		response=given()
				.spec(request)
				.header("userId", "test")
				.when()
				.post(ENDPOINT_OFF_STATE);
		else
			response=given()
					.spec(request)
					.header("userId", "test")
					.header("Content-Type", "application/json")
					.when()
					.post(ENDPOINT_ON_STATE);
			
	}

	@Then("^the bulb should turn (.*)$")
	public void theBulbShouldTurnState(String state) throws Throwable {
		json=response.getStatusCode();
		assertThat(json).isEqualTo(200);
	}

	@And("^the reported message should be (.*)$")
	public void theReportedMessageShouldBeMessage(String message) throws Throwable {
		result=response.getBody().as(LightBulb.class);
		assertThat(result.getResult()).isEqualTo(message);
	}
}


