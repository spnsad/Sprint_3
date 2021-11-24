import io.restassured.response.Response;
import org.json.JSONObject;
import static io.restassured.RestAssured.*;

public class CourierMethods {

    //Метод для регистрации курьера
    public Response registerNewCourierAndReturnResponse(String courierLogin, String courierPassword, String courierFirstName){

        JSONObject registerRequestBody = new JSONObject()
                .put("login",courierLogin)
                .put("password", courierPassword)
                .put("firstName", courierFirstName);

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");
        return response;
    }

    //Метод для логина курьера
    public Response loginCourier(String courierLogin, String courierPassword){

        JSONObject loginBody = new JSONObject()
                .put("login", courierLogin)
                .put("password", courierPassword);

        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(loginBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login");
        return response;
    }

    //Метод для получения id курьера
    public static Integer getCourierId(String courierLogin, String courierPassword){
        JSONObject loginBody = new JSONObject()
                .put("login", courierLogin)
                .put("password", courierPassword);

        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(loginBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login")
                .then().extract().response();

        Integer id = response.path("id");
        return id;
    }

    //Метод для получения id курьера и его дальнейшего удаления
    public static void loginAndDeleteCourier(String courierLogin, String courierPassword){
        JSONObject loginBody = new JSONObject()
                .put("login", courierLogin)
                .put("password", courierPassword);

        Response response = given()
                .header("Content-type","application/json")
                .and()
                .body(loginBody.toString())
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier/login")
                .then().extract().response();

        Response deleteResponse = given()
                .delete("https://qa-scooter.praktikum-services.ru/api/v1/courier/" + response.path("id"));
    }

    //Метод для удаления курьера
    public static void deleteCourier(Integer id){
        given().delete("https://qa-scooter.praktikum-services.ru/api/v1/courier/" + id);
    }
} 