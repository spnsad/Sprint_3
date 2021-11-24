import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class OrdersMethods {


    //Метод для создания заказа с выбором цвета
    public  Response createOrderWithParamColor(ArrayList<String> color){
        Random numberOfStation = new Random();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        JSONObject createRequestBody = new JSONObject()
                .put("firstName", RandomStringUtils.randomAlphabetic(10))
                .put("lastName", RandomStringUtils.randomAlphabetic(10))
                .put("address", RandomStringUtils.randomAlphabetic(20))
                .put("metroStation", numberOfStation.nextInt(10)+1)
                .put("phone", RandomStringUtils.randomAlphabetic(11))
                .put("rentTime",numberOfStation.nextInt(10)+1)
                .put("deliveryDate", timeStamp)
                .put("comment", RandomStringUtils.randomAlphabetic(30))
                .put("color", color);

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(createRequestBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders");
        return response;
    }

    //Метод для отмены заказа по его track
    public static Response cancelOrderByTrack(Integer track) {
        JSONObject cancelRequestBody = new JSONObject()
                .put("track", track);

        Response cancelResponse = given().header("Content-type", "application/json")
                .and()
                .body(cancelRequestBody.toString())
                .when()
                .put("https://qa-scooter.praktikum-services.ru/api/v1/orders/cancel");
        cancelResponse.then().statusCode(200);
        return cancelResponse;
    }

    //Метод для получения id заказа
    public Integer getOrderId(){
        Random numberOfStation = new Random();
        ArrayList<String> color = new ArrayList<>();
        color.add("BLACK");

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        //Собираем тело запроса на создание заказа
        JSONObject createRequestBody = new JSONObject()
                .put("firstName", RandomStringUtils.randomAlphabetic(10))
                .put("lastName", RandomStringUtils.randomAlphabetic(10))
                .put("address", RandomStringUtils.randomAlphabetic(20))
                .put("metroStation", numberOfStation.nextInt(10)+1)
                .put("phone", RandomStringUtils.randomAlphabetic(11))
                .put("rentTime",numberOfStation.nextInt(10)+1)
                .put("deliveryDate", timeStamp)
                .put("comment", RandomStringUtils.randomAlphabetic(30))
                .put("color", color);

        //Выполняем запрос на создание заказа
        Response createResponse =  given()
                .header("Content-type", "application/json")
                .and()
                .body(createRequestBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders")
                .then().extract().response();

        Integer orderTrack = createResponse.path("track");

        //Получаем id заказа по трек-номеру
        Response getIdResponse =  given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders/track?t=" + orderTrack)
                .then().extract().response();

        Integer orderId = getIdResponse.path("order.id");
        return orderId;
    }

    //Метод для принятия заказа курьером
    public void acceptOrder(Integer courierId, Integer orderId){
        Response acceptOrderResponse =  given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("https://qa-scooter.praktikum-services.ru/api/v1/orders/accept/" + orderId + "?courierId=" + courierId);
    }

    //Метод для получения заказов курьера по его id
    public Response getCourierOrdersList(Integer courierId){
        Response courierOrdersListResponse =  given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders?courierId="+courierId);
        return courierOrdersListResponse;
    }
}