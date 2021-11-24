import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {

    @Test
    @DisplayName("Получение списка заказов по id курьера")
    public void getOrdersListTest() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        //Регистрируем курьера
        new CourierMethods().registerNewCourierAndReturnResponse(courierLogin, courierPassword, courierFirstName);

        //Получаем его id
        Integer courierId = CourierMethods.getCourierId(courierLogin, courierPassword);

        //Создаем заказ и получаем его orderId внутри метода getOrderId
        Integer orderId = new OrdersMethods().getOrderId();

        //Принимаем заказ курьером
        new OrdersMethods().acceptOrder(courierId,orderId);

        //Получаем список заказов данного курьера
        Response getOrdersListResponse = new OrdersMethods().getCourierOrdersList(courierId);
        getOrdersListResponse.then().statusCode(200).and().body("orders.id",notNullValue()).extract().response();

        Assert.assertEquals("В ответе вернулся некорректный список заказов", orderId, getOrdersListResponse.path("orders.id"));

        //Отменяем заказ
        Integer orderTrack = getOrdersListResponse.path("orders.track");
        OrdersMethods.cancelOrderByTrack(orderTrack);

        //Удаляем курьера
        CourierMethods.deleteCourier(courierId);
    }
}
