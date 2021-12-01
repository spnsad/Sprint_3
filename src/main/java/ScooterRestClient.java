import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ScooterRestClient {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    protected static RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
