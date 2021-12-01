public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password = password;
    }

    public CourierCredentials(){
    }

    public static CourierCredentials from(Courier courier){
        return new CourierCredentials(courier.login, courier.password);
    }

    public CourierCredentials setLogin(String login){
        this.login = login;
        return this;
    }

    public CourierCredentials setPassword(String password){
        this.password = password;
        return this;
    }

    public static CourierCredentials withLoginOnly(Courier courier){
        return new CourierCredentials().setLogin(courier.login);
    }

    public static CourierCredentials withPasswordOnly(Courier courier){
        return new CourierCredentials().setPassword(courier.password);
    }

    public static CourierCredentials nonRegistered(String login, String password){
        return new CourierCredentials(login, password);
    }
}
