import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private Courier courier;
    private static CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.createRandomCourier();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Регистрация курьера с корректными данными")
    public void checkCreateCourierWithValidData() {

        ValidatableResponse response = courierClient.createCourier(courier);
        courierId = CourierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();

        assertThat("Status code is incorrect", statusCode, equalTo(201));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Повторная регистрация курьера")
    public void checkCreateRepeatedCourier(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Courier is created", errorMessage, equalTo("Этот логин уже используется. Попробуйте другой."));
        assertThat("Status code is incorrect", statusCode, equalTo(409));
    }
}
