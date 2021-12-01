import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierWORequiredParamTest{
    private Courier courier;
    private CourierClient courierClient;
    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        courier = Courier.createRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
    }

    @Test
    @DisplayName("Проверка логина курьера без указания логина")
    public void checkLoginWOLogin(){
        ValidatableResponse response = CourierClient.loginCourier(CourierCredentials.withPasswordOnly(courier));
        String errorMessage = response.extract().path("message");
        int statusCode = response.extract().statusCode();

        assertThat("Status code is incorrect", statusCode, equalTo(400));
        assertThat("Response message is not correct", errorMessage, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка логина курьера без указания пароля")
    public void checkLoginWOPassword(){
        ValidatableResponse response = CourierClient.loginCourier(CourierCredentials.withLoginOnly(courier));

        assertThat("Status code is incorrect", response.extract().statusCode(), equalTo(400));
        assertThat("Response message is not correct", response.extract().path("message"), equalTo("Недостаточно данных для входа"));
    }

}
