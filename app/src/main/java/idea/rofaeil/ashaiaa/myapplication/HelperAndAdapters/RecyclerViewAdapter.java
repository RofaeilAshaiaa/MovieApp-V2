package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    private ArrayList<Movie> mMovieArrayList ;
    private Context mContext;
    private FragmentActivity mActivity ;

    public RecyclerViewAdapter(ArrayList<Movie> mMovieArrayList, Context mContext , FragmentActivity activity) {
        this.mMovieArrayList = mMovieArrayList;
        this.mContext = mContext;
        this.mActivity = activity ;
    }

    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewItemBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_item, parent, false);

        return new myViewHolder( viewDataBinding);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

//        Uri uri = Uri.parse("http://i.imgur.com/DvpvklR.png");
//        holder.itemBinding.rvImageViewItem.setImageURI(uri);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int heightPixels = displaymetrics.heightPixels;
        int targetY = 2 * (heightPixels /5) ;
        holder.itemBinding.rvImageViewItem.setMinimumHeight(targetY);
        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png")
                .into(holder.itemBinding.rvImageViewItem);


    }

    @Override
    public int getItemCount() {
        return 10 ;
//        return mMovieArrayList.size();
    }

    public  class myViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public RecyclerviewItemBinding itemBinding;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public myViewHolder(RecyclerviewItemBinding itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any myViewHolder instance.
            super(itemView.getRoot());

            itemBinding = itemView  ;
        }
    }
}
