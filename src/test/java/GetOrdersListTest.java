import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {
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
    @DisplayName("Получение списка заказов по id курьера")
    public void getOrdersListTest() {

        //Получаем id курьера
        ValidatableResponse response = CourierClient.loginCourier(CourierCredentials.from(courier));
        courierId = response.extract().path("id");

        //Создаем заказ и получаем его orderId внутри метода getOrderId
        Integer orderId = new Orders().getOrderId();

        //Принимаем заказ курьером
        new Orders().acceptOrder(courierId,orderId);

        //Получаем список заказов данного курьера
        Response getOrdersListResponse = new Orders().getCourierOrdersList(courierId);
        getOrdersListResponse.then().statusCode(200).and().body("orders.id",notNullValue()).extract().response();

        Assert.assertEquals("В ответе вернулся некорректный список заказов", orderId, getOrdersListResponse.path("orders.id"));

        //Отменяем заказ
        Integer orderTrack = getOrdersListResponse.path("orders.track");
        Orders.cancelOrderByTrack(orderTrack);
    }
}
