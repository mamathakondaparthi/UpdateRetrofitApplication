package capture.ivis.com.updateretrofitapplication.my_interface;

import capture.ivis.com.updateretrofitapplication.Model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetNoticeDataService {

//    @GET
//    Call<User> showDetailsList(@Url String url);


    @GET("api/users?page=1")
    Call<User> showDetailsList();

    // @GET
    //Call<List<LocationModel>> locationUpdateList(@Url String url);

}
