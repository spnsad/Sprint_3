import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierWIthNonExistDataTest {

    String login = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomAlphabetic(10);

    @Test
    @DisplayName("Проверка логина курьера по несуществующим данным")
    public void checkLoginWithNonExistentData(){
        ValidatableResponse response = new CourierClient().loginCourier(CourierCredentials.nonRegistered(login, password));
        String errorMessage = response.extract().path("message");
        int statusCode = response.extract().statusCode();

        assertThat("Status code is incorrect", statusCode, equalTo(404));
        assertThat("Response message is incorrect", errorMessage, equalTo("Учетная запись не найдена"));
    }
}
