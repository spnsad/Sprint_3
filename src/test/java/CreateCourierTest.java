import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;


public class CreateCourierTest {

    @Test
    @DisplayName("Успешная регистрация курьера")
    public void checkSuccessCreateCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        Response response = new CourierMethods().registerNewCourierAndReturnResponse(login, password, firstName);
        response.then().statusCode(201)
                .and().assertThat().body("ok", equalTo(true));

        CourierMethods.loginAndDeleteCourier(login, password);
    }

    @Test
    @DisplayName("Регистрация курьера без указания логина")
    public void checkCreateCourierWOLogin() {
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        Response resp = new CourierMethods().registerNewCourierAndReturnResponse(null, password, firstName);
        resp.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистрация курьера без указания пароля")
    public void checkCreateCourierWOPassword(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        Response resp = new CourierMethods().registerNewCourierAndReturnResponse(login, null, firstName);
        resp.then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистрация курьера с повторяющимся логином")
    public void checkCreateRepeatedCourier(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        Response responseFirst = new CourierMethods().registerNewCourierAndReturnResponse(login, password, firstName);
        responseFirst.then().statusCode(201);

        Response responseRepeat = new CourierMethods().registerNewCourierAndReturnResponse(login,password, firstName);
        responseRepeat.then().statusCode(409)
                .and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        CourierMethods.loginAndDeleteCourier(login,password);
    }
}