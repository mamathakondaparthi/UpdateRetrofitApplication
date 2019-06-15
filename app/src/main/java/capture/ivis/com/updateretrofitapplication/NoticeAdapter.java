package capture.ivis.com.updateretrofitapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import capture.ivis.com.updateretrofitapplication.Model.Datum;

public class NoticeAdapter  extends  RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private List<Datum> dataList;
    private Context mContext;
    private DisplayImageOptions options;

    public NoticeAdapter(List<Datum> dataList, Context applicationContext) {
        this.dataList = dataList;
        this.mContext = applicationContext;
        //ImageLoader code
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_iamge)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singleitemview, parent, false);
        return new NoticeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NoticeViewHolder holder, int position) {
        holder.txtEmail.setText(dataList.get(position).getEmail());
        holder.txtFirstname.setText(dataList.get(position).getFirstName());
        holder.txtLastName.setText(dataList.get(position).getLastName());
        //Picasso.with(mContext).load(dataList.get(position).getAvatar()).placeholder(R.mipmap.ic_launcher).into(holder.ivImageView);;

        //or using imageLoader
        ImageLoader.getInstance()
                .displayImage(dataList.get(position).getAvatar(), holder.ivImageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView txtEmail, txtFirstname, txtLastName;
        ImageView ivImageView;
        ProgressBar progressBar;

        NoticeViewHolder(View itemView) {
            super(itemView);
            txtEmail = itemView.findViewById(R.id.tv_email);
            txtFirstname = itemView.findViewById(R.id.txt_firstname);
            txtLastName = itemView.findViewById(R.id.txt_lastname);
            ivImageView = itemView.findViewById(R.id.iv_notice_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);


        }
    }

}
