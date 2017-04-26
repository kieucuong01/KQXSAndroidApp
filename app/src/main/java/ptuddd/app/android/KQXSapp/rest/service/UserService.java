package ptuddd.app.android.KQXSapp.rest.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cuong Kieu on 4/4/2017.
 */
public interface UserService {

    @GET("/kqxsmn")
    Call<HashMap> kqxsmn();

}
