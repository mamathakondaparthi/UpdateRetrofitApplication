package capture.ivis.com.updateretrofitapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

import capture.ivis.com.updateretrofitapplication.Model.Datum;
import capture.ivis.com.updateretrofitapplication.Model.User;
import capture.ivis.com.updateretrofitapplication.my_interface.GetNoticeDataService;
import capture.ivis.com.updateretrofitapplication.nertwork.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private NoticeAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imageLoader code
        initImageLoader(getApplicationContext());
        ShowDetails();
    }

    //imageLoader code start
        public static void initImageLoader(Context context) {
            // This configuration tuning is custom. You can tune every option, you may tune some of them,
            // or you can create default configuration by
            //  ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
            config.threadPriority(Thread.NORM_PRIORITY - 2);
            config.denyCacheImageMultipleSizesInMemory();
            config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
            config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
            config.tasksProcessingOrder(QueueProcessingType.LIFO);
            config.writeDebugLogs(); // Remove for release app

            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config.build());
            //imageLoader code end
        }



    private void ShowDetails() {
        GetNoticeDataService service = RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);

       // String url = "api/users?page=1";

        Call<User> call = service.showDetailsList();


        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                generateNoticeList(response.body().getData());
                Log.d("response", String.valueOf(response.body().getData()));

                Toast.makeText(MainActivity.this, "Successfully open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("error",t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateNoticeList(List<Datum> noticeArrayList) {
        recyclerView = findViewById(R.id.recycler_view_notice_list);
        adapter = new NoticeAdapter(noticeArrayList,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
