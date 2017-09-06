package deerlight.com.practice_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_HOST = "https://api.github.com/";
    private static final String USERNAME = "Samuel-Hsieh";
    private static final String USER_PATH = "users/";
    private static final String RETROFIT = "Retrofit";
    private static final String VOLLEY = "Volley";
    private static final String FAST_ANDROID_NETWORKING = "FastAndroidNetworking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitConnect();
        VolleyConnect();
        FastAndroidNetworkingConnect();
    }

    /**
     * Volley
     */
    private void VolleyConnect() {
        final long start = System.currentTimeMillis();
        String UserApiUrl = API_HOST + USER_PATH + USERNAME;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(UserApiUrl, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserBean userBean = new Gson().fromJson(response, UserBean.class);
                Log.d(VOLLEY, "Id: " + userBean.getId());
                Log.d(VOLLEY, "Name: " + userBean.getName());
                Log.d(VOLLEY, "Blog: " + userBean.getBlog());
                Log.d(VOLLEY, "Followers: " + userBean.getFollowers());
                Log.d(VOLLEY, "FollowersUrl: " + userBean.getFollowersUrl());
                Log.d(VOLLEY, "Email: " + userBean.getEmail());
                Log.d(VOLLEY, "SpendTime: " + (System.currentTimeMillis() - start) + "ms");
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(VOLLEY, "error: " + error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

    /**
     * Retrofit
     */
    private void RetrofitConnect() {
        final long start = System.currentTimeMillis();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiEndpointInterface apiService = retrofit.create(ApiEndpointInterface.class);
        Call<UserBean> call = apiService.getUser(USERNAME);
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                int statusCode = response.code();
                UserBean userBean = response.body();
                Log.d(RETROFIT, "statusCode: " + statusCode);
                Log.d(RETROFIT, "Id: " + userBean.getId());
                Log.d(RETROFIT, "Name: " + userBean.getName());
                Log.d(RETROFIT, "Blog: " + userBean.getBlog());
                Log.d(RETROFIT, "Followers: " + userBean.getFollowers());
                Log.d(RETROFIT, "FollowersUrl: " + userBean.getFollowersUrl());
                Log.d(RETROFIT, "Email: " + userBean.getEmail());
                Log.d(RETROFIT, "SpendTime: " + (System.currentTimeMillis() - start) + "ms");
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                t.printStackTrace();
                Log.d(RETROFIT, "onFailure_Message: " + t.getMessage());
            }
        });
    }

    /**
     * FastAndroidNetworking
     */
    private void FastAndroidNetworkingConnect() {
        final long start = System.currentTimeMillis();
        String UserApiUrl = API_HOST + USER_PATH + USERNAME;
        AndroidNetworking.get(UserApiUrl)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                UserBean userBean = new Gson().fromJson(response.toString(), UserBean.class);
                Log.d(FAST_ANDROID_NETWORKING, "Id: " + userBean.getId());
                Log.d(FAST_ANDROID_NETWORKING, "Name: " + userBean.getName());
                Log.d(FAST_ANDROID_NETWORKING, "Blog: " + userBean.getBlog());
                Log.d(FAST_ANDROID_NETWORKING, "Followers: " + userBean.getFollowers());
                Log.d(FAST_ANDROID_NETWORKING, "FollowersUrl: " + userBean.getFollowersUrl());
                Log.d(FAST_ANDROID_NETWORKING, "Email: " + userBean.getEmail());
                Log.d(FAST_ANDROID_NETWORKING, "SpendTime: " + (System.currentTimeMillis() - start) + "ms");
            }

            @Override
            public void onError(ANError anError) {
                anError.printStackTrace();
                Log.d(FAST_ANDROID_NETWORKING, "onError: " + anError.getMessage());
            }
        });
    }
}
