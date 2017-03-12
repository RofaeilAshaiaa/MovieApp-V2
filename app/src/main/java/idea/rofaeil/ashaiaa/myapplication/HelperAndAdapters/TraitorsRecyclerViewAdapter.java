package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewItemBinding;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewTrailorItemBinding;

public class TraitorsRecyclerViewAdapter extends RecyclerView.Adapter<TraitorsRecyclerViewAdapter.myViewHolder>{

    final private ListItemClickListener itemClickListener ;
    private ArrayList<String> mTrailersList;
    private Context mContext;

    public TraitorsRecyclerViewAdapter(ArrayList<String> mMovieArrayList, Context mContext
                                                     , ListItemClickListener itemClickListener ) {
        this.mTrailersList = mMovieArrayList;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public TraitorsRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewTrailorItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.recyclerview_trailor_item, parent, false);
        return new myViewHolder( view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

//        holder.itemBinding.rvImageViewItem.setMinimumHeight(MainActivity.targetY);\
        holder.itemBinding.rvTextViewItem.setText("Trailer "+ (position+1) );

    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    public interface ListItemClickListener{
        public void onListItemTrailerClicked(int clickedItemIndex);
    }

    public  class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RecyclerviewTrailorItemBinding itemBinding;


        public myViewHolder(RecyclerviewTrailorItemBinding itemView) {
            super(itemView.getRoot());

            itemBinding = itemView  ;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition() ;
            itemClickListener.onListItemTrailerClicked(clickedPosition);
        }
    }
}
