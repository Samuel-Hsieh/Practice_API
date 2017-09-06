package deerlight.com.practice_api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by samuel_hsieh on 2017/9/6.
 */

public interface ApiEndpointInterface {

    @GET("/users/{username}")
    Call<UserBean> getUser(@Path("username") String username);
}
