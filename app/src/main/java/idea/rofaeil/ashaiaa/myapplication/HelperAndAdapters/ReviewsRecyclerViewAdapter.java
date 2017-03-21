package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.Objects.Review;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewReviewItemBinding;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.myViewHolder>{

    final private ListItemClickListener itemClickListener ;
    private ArrayList<Review> mTrailersList;
    private Context mContext;

    public ReviewsRecyclerViewAdapter(ArrayList<Review> mMovieArrayList, Context mContext
                                                     , ListItemClickListener itemClickListener ) {
        this.mTrailersList = mMovieArrayList;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public ReviewsRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerviewReviewItemBinding view =
                DataBindingUtil.inflate(inflater, R.layout.recyclerview_review_item, parent, false);
        return new myViewHolder( view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.itemBinding.tvReviewAuthor.setText(mTrailersList.get(position).getAuthor());
        holder.itemBinding.tvExpandableLayout.setText(mTrailersList.get(position).getReviewContent());

    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    public interface ListItemClickListener{
        public void onListItemReviewClicked(int clickedItemIndex, RecyclerviewReviewItemBinding itemBinding);
    }

    public  class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RecyclerviewReviewItemBinding itemBinding;


        public myViewHolder(RecyclerviewReviewItemBinding itemView) {
            super(itemView.getRoot());

            itemBinding = itemView  ;
            itemView.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition() ;
            itemClickListener.onListItemReviewClicked(clickedPosition , itemBinding);
        }
    }
}
