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

import idea.rofaeil.ashaiaa.myapplication.MainClasses.MainActivity;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    private ArrayList<Movie> mMovieArrayList ;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Movie> mMovieArrayList, Context mContext ) {
        this.mMovieArrayList = mMovieArrayList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.recyclerview_item, parent, false);
        return new myViewHolder( view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.itemBinding.rvImageViewItem.setMinimumHeight(MainActivity.targetY);

        Picasso.with(mContext).load(mMovieArrayList.get(position).getMoviePoster())
                 .placeholder(R.drawable.ic_favorite_border_black_24dp)
                .error(R.drawable.ic_language_black_24dp)
                .fit().into(holder.itemBinding.rvImageViewItem);
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }

    public  class myViewHolder extends RecyclerView.ViewHolder {

        public RecyclerviewItemBinding itemBinding;


        public myViewHolder(RecyclerviewItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView  ;
        }
    }
}
