import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderWithParamScooterColorsTest {

    private final ArrayList<String> colors;
    private final Integer expectedRespCode;


    public CreateOrderWithParamScooterColorsTest(ArrayList<String> colors, int expectedRespCode) {
        this.colors = colors;
        this.expectedRespCode = expectedRespCode;
    }

    @Parameterized.Parameters
    public static Object[][] scooterColors() {
        ArrayList<String> blackColor = new ArrayList<>();
        blackColor.add("BLACK");

        ArrayList<String> greyColor = new ArrayList<>();
        greyColor.add("GREY");

        ArrayList<String> greyAndBlackColors = new ArrayList<>();
        greyAndBlackColors.add("BLACK");
        greyAndBlackColors.add("GREY");

        return new Object[][] {
                {blackColor, 201},
                {greyColor, 201},
                {greyAndBlackColors, 201},
                {null, 201}
        };
    }

    @Test
    @DisplayName("Создание заказа с выбором цвета самоката")
    public void createOrderWithDifferentScooterColors(){
        Response createOrderResponse = new OrdersMethods().createOrderWithParamColor(colors);
        createOrderResponse.then().statusCode(expectedRespCode)
                .and().assertThat().body("track", notNullValue()).extract().response();

        OrdersMethods.cancelOrderByTrack(createOrderResponse.path("track"));
    }
}