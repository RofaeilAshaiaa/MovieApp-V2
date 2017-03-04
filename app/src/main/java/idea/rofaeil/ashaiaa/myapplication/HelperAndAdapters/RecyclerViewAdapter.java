package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    private ArrayList<Movie> mMovieArrayList ;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Movie> mMovieArrayList, Context mContext) {
        this.mMovieArrayList = mMovieArrayList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewItemBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_item, parent, false);

        return new myViewHolder( viewDataBinding);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.itemBinding.rvImageViewItem.
                setImageResource(R.drawable.pic);

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
