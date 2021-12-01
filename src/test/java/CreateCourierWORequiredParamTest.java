import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierWORequiredParamTest {

    private static Courier courier;
    private static CourierClient courierClient;

    @Before
    public void setUp() {
        courier = Courier.createRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Регистрация курьера без указания логина")
    public void checkCreateWOLogin(){
        ValidatableResponse response = courierClient.createCourier(courier.withPasswordOnly());
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Courier is created, it's not correct", errorMessage, equalTo("Недостаточно данных для создания учетной записи"));
        assertThat("Status code is incorrect", statusCode, equalTo(400));
    }

    @Test
    @DisplayName("Регистрация курьера без указания пароля")
    public void checkCreateWOPassword(){
        ValidatableResponse response = courierClient.createCourier(courier.withLoginOnly());
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Courier is created, it's not correct", errorMessage, equalTo("Недостаточно данных для создания учетной записи"));
        assertThat("Status code is incorrect", statusCode, equalTo(400));
    }
}
