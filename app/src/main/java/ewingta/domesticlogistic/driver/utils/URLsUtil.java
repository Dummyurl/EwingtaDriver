package ewingta.domesticlogistic.driver.utils;

public class URLsUtil {
    public static final String BASE_URL = "http://domesticlogistics.in/beta/";

    private static final String INDEX_POST_URL = BASE_URL + "index.php?option=com_jbackend&view=request&module=user&action=post";

    private static final String INDEX_GET_URL = BASE_URL + "index.php?option=com_jbackend&view=request&module=user&action=get";

    public static final String LOGIN_URL = INDEX_POST_URL + "&resource=login";

    public static final String REGISTER_URL = INDEX_POST_URL + "&resource=register";

    public static final String OTP_URL = INDEX_GET_URL + "&resource=otpactivation&usertype=D";

    public static final String ORDERS_URL = INDEX_GET_URL + "&resource=orderlist";

    public static final String ADD_ADDRESS_URL = INDEX_POST_URL + "&resource=addressadd";

    public static final String LIST_ADDRESS_URL = INDEX_GET_URL + "&resource=useraddress";

    public static final String SERVICES_URL = INDEX_GET_URL + "&resource=services";

    public static final String CATEGORIES_URL = INDEX_GET_URL + "&resource=category";

    public static final String TIMES_URL = INDEX_GET_URL + "&resource=times";

    public static final String AREA_URL = INDEX_GET_URL + "&resource=location&countryid=1&stateid=3";

    public static final String CITY_URL = INDEX_GET_URL + "&resource=city&countryid=1&stateid=3";

    public static final String ADDRESSES_URL = INDEX_GET_URL + "&resource=useraddress";

    public static final String ADD_ORDER_URL = INDEX_POST_URL + "&resource=ordergenarate&country=1&state=3";

    public static final String UPDATE_PASSWORD_URL = INDEX_GET_URL + "&resource=updateprofile";

    public static final String PRICE_URL = INDEX_GET_URL + "&resource=getpricea";

    public static final String VERIFYOTP_URL =INDEX_GET_URL + "&resource=otpsendtodriver";
}

