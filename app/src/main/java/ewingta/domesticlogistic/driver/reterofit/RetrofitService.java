package ewingta.domesticlogistic.driver.reterofit;


import ewingta.domesticlogistic.driver.models.AddAddressResponse;
import ewingta.domesticlogistic.driver.models.AddressResponse;
import ewingta.domesticlogistic.driver.models.AreaResponse;
import ewingta.domesticlogistic.driver.models.CategoryResponse;
import ewingta.domesticlogistic.driver.models.CityResponse;
import ewingta.domesticlogistic.driver.models.LoginResponse;
import ewingta.domesticlogistic.driver.models.MyAddressResponse;
import ewingta.domesticlogistic.driver.models.OrderResponse;
import ewingta.domesticlogistic.driver.models.PriceResponse;
import ewingta.domesticlogistic.driver.models.RegisterResponse;
import ewingta.domesticlogistic.driver.models.ServiceResponse;
import ewingta.domesticlogistic.driver.models.TimeResponse;
import ewingta.domesticlogistic.driver.models.VerifyResponse;
import ewingta.domesticlogistic.driver.utils.URLsUtil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET(URLsUtil.LOGIN_URL)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);

    @GET(URLsUtil.REGISTER_URL)
    Call<RegisterResponse> registerUser(@Query("name") String name, @Query("email") String email, @Query("mobile") String mobile, @Query("password") String password);

    @GET(URLsUtil.ORDERS_URL)
    Call<OrderResponse> getOrders(@Query("userid") String userid);

    @GET(URLsUtil.OTP_URL)
    Call<LoginResponse> otpActivation(@Query("userid") String userid, @Query("otp") String otp);

    @GET(URLsUtil.ADD_ADDRESS_URL)
    Call<AddAddressResponse> addAddress(@Query("userid") String userid, @Query("shortname") String shortname, @Query("location") String location);

    @GET(URLsUtil.LIST_ADDRESS_URL)
    Call<MyAddressResponse> listAddress(@Query("userid") String userid);

    @GET(URLsUtil.SERVICES_URL)
    Call<ServiceResponse> getServices();

    @GET(URLsUtil.CATEGORIES_URL)
    Call<CategoryResponse> getCategories();

    @GET(URLsUtil.TIMES_URL)
    Call<TimeResponse> getTimes();

    @GET(URLsUtil.AREA_URL)
    Call<AreaResponse> getAreas(@Query("cityid") long cityid);

    @GET(URLsUtil.CITY_URL)
    Call<CityResponse> getCities();

    @GET(URLsUtil.ADDRESSES_URL)
    Call<AddressResponse> getUserAddresses(@Query("userid") String userid);

    @GET(URLsUtil.ADD_ORDER_URL)
    Call<RegisterResponse> addOrder(@Query("userid") String userid,
                                    @Query("city") long city,
                                    @Query("branch") long branch,
                                    @Query("dropname") String dropname,
                                    @Query("dropphone") String dropphone,
                                    @Query("dropaddress") long dropaddress,
                                    @Query("pickupname") String pickupname,
                                    @Query("pickupphone") String pickupphone,
                                    @Query("pickupaddress") long pickupaddress,
                                    @Query("time") long time,
                                    @Query("type") long type,
                                    @Query("delverytype") long delverytype,
                                    @Query("datepick") String datepick);

    @GET(URLsUtil.UPDATE_PASSWORD_URL)
    Call<RegisterResponse> updatePassword(@Query("userid") String userid, @Query("name") String name,
                                          @Query("email") String email, @Query("mobile") String mobile, @Query("pass") String pass);

    @GET(URLsUtil.PRICE_URL)
    Call<PriceResponse> getPrice(@Query("ordernumber") String ordernumber);

    @GET(URLsUtil.VERIFYOTP_URL)
    Call<VerifyResponse> otpverifyActivation(@Query("userid") String userid, @Query("otp") String otp);
}
