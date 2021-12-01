import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;

public class LoginCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;
    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        courier = Courier.createRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Проверка успешного логина курьера")
    public void checkSuccessLoginCourier(){
        ValidatableResponse response = CourierClient.loginCourier(CourierCredentials.from(courier));
        courierId = response.extract().path("id");
        int statusCode = response.extract().statusCode();

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}
