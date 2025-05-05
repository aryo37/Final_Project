package helper;

public class Endpoint {
    public static final String host_dummyapi = "https://dummyapi.io/data/v1/";

    public static final String GET_LIST_USERS = host_dummyapi + "user";

    public static final String GET_USER_BY_ID = host_dummyapi + "user/<user-id>";

    public static final String CREATE_NEW_USER = host_dummyapi + "user/create";

    public static final String UPDATE_USER = host_dummyapi + "user/<user-id>";

    public static final String DELETE_USER = host_dummyapi + "user/<user-id>";
}
