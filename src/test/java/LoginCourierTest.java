import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

    @Test
    @DisplayName("Проверка успешного логина курьера")
    public void checkSuccessLoginCourier(){

        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        new CourierMethods().registerNewCourierAndReturnResponse(login, password, firstName);

        Response loginResponse = new CourierMethods().loginCourier(login, password);
        loginResponse.then().statusCode(200)
                .and().assertThat().body("id", notNullValue())
                .extract().response();

        CourierMethods.deleteCourier(loginResponse.path("id"));
    }

    @Test
    @DisplayName("Проверка логина курьера без указания логина")
    public void checkLoginCourierWOLogin() {
        String password = RandomStringUtils.randomAlphabetic(10);
        Response loginResponse = new CourierMethods().loginCourier(null, password);
        loginResponse.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка логина курьера без указания пароля")
    public void checkLoginCourierWOPassword(){
        String login = RandomStringUtils.randomAlphabetic(10);
        Response loginResponse = new CourierMethods().loginCourier(login, null);
        loginResponse.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка логина курьера по несуществующим данным")
    public void checkLoginWithNonExistentLogPass(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        Response loginResponse = new CourierMethods().loginCourier(login,password);
        loginResponse.then().statusCode(404)
                .and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
