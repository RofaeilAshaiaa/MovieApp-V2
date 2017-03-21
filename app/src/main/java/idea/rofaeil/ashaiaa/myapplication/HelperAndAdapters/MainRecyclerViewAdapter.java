package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.MainClasses.MainActivity;
import idea.rofaeil.ashaiaa.myapplication.Objects.Movie;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewItemBinding;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.myViewHolder>{

    final private ListItemClickListener itemClickListener ;
    private ArrayList<Movie> mMovieArrayList ;
    private Context mContext;

    public MainRecyclerViewAdapter(ArrayList<Movie> mMovieArrayList, Context mContext
                                                     , ListItemClickListener itemClickListener ) {
        this.mMovieArrayList = mMovieArrayList;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public MainRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.recyclerview_item, parent, false);
        return new myViewHolder( view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.itemBinding.rvImageViewItem.setMinimumHeight(MainActivity.targetY);

        Picasso.with(mContext).load(mMovieArrayList.get(position).getMoviePoster())
                 .placeholder(R.drawable.ic_autorenew_black_24dp)
                .error(R.drawable.ic_error_outline_black_24dp)
                .fit().into(holder.itemBinding.rvImageViewItem);
    }

    @Override
    public int getItemCount() {

        if(mMovieArrayList == null )return 0 ;

        return mMovieArrayList.size();
    }

    public interface ListItemClickListener{
        void onListItemClicked(int clickedItemIndex);
    }

    public  class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RecyclerviewItemBinding itemBinding;


        public myViewHolder(RecyclerviewItemBinding itemView) {
            super(itemView.getRoot());

            itemBinding = itemView  ;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition() ;
            itemClickListener.onListItemClicked(clickedPosition);
        }
    }
}
