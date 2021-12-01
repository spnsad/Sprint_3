import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;


public class CourierClient extends ScooterRestClient{
    private static final String COURIER_ENDPOINT = "api/v1/courier/";

    @Step
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_ENDPOINT)
                .then();
    }

    @Step
    public static void deleteCourier(int courierId){
        given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_ENDPOINT + courierId)
                .then();
    }

    @Step
    public static ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_ENDPOINT+ "login")
                .then();
    }
}
