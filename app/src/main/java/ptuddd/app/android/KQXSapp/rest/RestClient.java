package ptuddd.app.android.KQXSapp.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ptuddd.app.android.KQXSapp.rest.service.UserService;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by Cuong Kieu on 4/4/2017.
 */
public final class RestClient {
    public static final String API_BASE_URL = "http://thanhhungqb.tk:8080"; // TODO use portalId
    private static RestClient instance = new RestClient();

    private Retrofit retrofit;
    private OkHttpClient httpClient;
    private UserService userService;
    private String authToken;


    private RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();   //
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.addInterceptor(logging);
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                if (authToken != null) {                                  // xac thuc bang authToken
                    requestBuilder.header("Authorization", authToken);
                }
                String contentType = original.header("Content-Type");
                if (contentType == null) {
                    requestBuilder.header("Content-Type", "application/json")
                            .method(original.method(), original.body());
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        httpBuilder.addInterceptor(requestInterceptor);
        httpBuilder.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES);

        httpClient = httpBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(createObjectMapper()))
                .client(httpClient)
                .build();
    }
    // ham thuc hien chuc nang hien tai
    public static RestClient getInstance() {
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public static String getApiBaseUrl() {
        return API_BASE_URL;
    }


    public UserService getUserService() {
        if (userService == null) {
            userService = createService(UserService.class); // tao 1 dich vụ class UserService
        }
        return userService;
    }


    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }


}
