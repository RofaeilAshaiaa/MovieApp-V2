package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MoviesDbHelper;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.NetworkAsyncTaskLoader;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Review;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.ReviewsRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.TraitorsRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MovieDetailsFragmentBinding;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewReviewItemBinding;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
        ,TraitorsRecyclerViewAdapter.ListItemClickListener, ReviewsRecyclerViewAdapter.ListItemClickListener {


    private final int TOP_RATED_LOADER_ID = 33;
    private MovieDetailsFragmentBinding mBinding;
    private ProgressBar mProgressBar;
    private JSONObject response ;
    private Context mContext ;
    private FragmentActivity mDetailActivity;
    private StringBuilder URL;
    private int Movie_ID;
    private SQLiteDatabase mDB ;
    private Movie mMovie;
    private ArrayList<String> mTrailersList ;
    private ArrayList<Review> mReviewList ;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Movie_ID = getArguments().getInt(getString(R.string.movie_id_string));
        mBinding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
        mDetailActivity = getActivity();
        mContext = getContext() ;

        mProgressBar =  mBinding.pbMovieDetailsFragment;
        makeNetworkRequest();
        setOnClickListenerFavouriteMeImage();
        return mBinding.getRoot();
    }

    private void setOnClickListenerFavouriteMeImage() {
        mBinding.ivAddToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviesDbHelper dbHelper = new MoviesDbHelper(mContext) ;
                mDB = dbHelper.getWritableDatabase();
                MoviesDbHelper.addMovieToFavourites(mMovie , mDB ,mContext);
                mBinding.ivAddToFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
            }
        });
    }

    private void makeNetworkRequest() {

        URL = new StringBuilder("https://api.themoviedb.org/3/movie/")
                .append(Movie_ID)
                .append("?api_key=27c5319da038dffe1e6957609d9797a0&append_to_response=videos,reviews");

        mProgressBar.setVisibility(View.VISIBLE);
        mBinding.clMovieDetails.setVisibility(View.INVISIBLE);
        LoaderManager loaderManager = mDetailActivity.getSupportLoaderManager();
        //Loader<String> popularLoader = loaderManager.getLoader(TOP_RATED_LOADER_ID);
        loaderManager.initLoader(TOP_RATED_LOADER_ID, null, this).forceLoad();

//        if(popularLoader == null)
//        {
//          loaderManager.initLoader(POPULAR_LOADER_ID,null,this).forceLoad();
//        }else {
//            loaderManager.restartLoader(POPULAR_LOADER_ID,null,this).startLoading();
//        }

    }

    private void setBasicDataOfMovie() throws JSONException {
        mMovie= new Movie() ;
        String poster_path = response.getString("poster_path");
        StringBuilder base_url = new StringBuilder("http://image.tmdb.org/t/p/");
        base_url.append("w185/").append(poster_path);
        String url = base_url.toString();
        mMovie.setMoviePoster(url);

            Picasso.with(getActivity().getBaseContext()).load(url)
                    .error(R.drawable.ic_error_outline_black_24dp).fit()
                    .tag(getActivity().getBaseContext()).into(mBinding.ivMoviePoster);

        String originalTitle = response.getString("original_title");
        mMovie.setOriginalTitle(originalTitle);
        if(true){
            Toolbar toolbar = (Toolbar) mDetailActivity.findViewById(R.id.tb_movie_details_activity);
            toolbar.setTitle(originalTitle);

        }else {

        }

        String releaseDate = response.getString("release_date");
        mMovie.setReleaseDate(releaseDate);
        mBinding.tvRealseDate.setText(releaseDate);

        String runtime = response.getString("runtime");
        mMovie.setRuntime(runtime);
        mBinding.tvMovieDuration.setText(runtime + "min.");

        double vote_average = response.getDouble("vote_average");
        float rating =(float) vote_average/2 ;
        mMovie.setVoteAverage(Float.toString(rating));
        mBinding.rbMovieRating.setRating(rating);

        String overview = response.getString("overview");
        mMovie.setMovieOverview(overview);
        mBinding.tvOverviewOfMovie.setText(overview);

    }

    private void setTrailersMovie() throws JSONException {

        JSONArray videos_array = response.getJSONObject("videos").getJSONArray("results");
        int numberOfTrailers = videos_array.length();
        mTrailersList = new ArrayList<>(numberOfTrailers);

        for (int i = 0; i < numberOfTrailers; i++) {

            JSONObject video_content_json_in_loop = (JSONObject) videos_array.get(i);
            String mVideoKey = video_content_json_in_loop.getString("key");

            StringBuilder mUrl = new StringBuilder("https://www.youtube.com/watch?v=");
            mUrl.append(mVideoKey);
            mTrailersList.add(mUrl.toString());

        }

        if(numberOfTrailers != 0  ){

            mBinding.rvTrailors.setAdapter(new TraitorsRecyclerViewAdapter(mTrailersList,mContext, this));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL ,false);
            mBinding.rvTrailors.setLayoutManager(mLayoutManager);
        }
    }

    private void setReviewsMovie() throws JSONException {

        JSONArray reviewList = response.getJSONObject("reviews").getJSONArray("results");
        int numberOfReviews = reviewList.length();
        mReviewList = new ArrayList<>(numberOfReviews);

        for (int i = 0; i < numberOfReviews; i++) {

            JSONObject reviewContentJsonObject = (JSONObject) reviewList.get(i);
            String reviewContent = reviewContentJsonObject.getString("content");
            String reviewAuthor = reviewContentJsonObject.getString("author");
            Review review = new Review(reviewAuthor,reviewContent) ;
            mReviewList.add(review);
        }

        if(numberOfReviews != 0){
            mBinding.rvReviews.setAdapter(new ReviewsRecyclerViewAdapter(mReviewList,mContext, this));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL ,false);
            mBinding.rvReviews.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new NetworkAsyncTaskLoader(mContext, URL.toString());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        try {
            response = new JSONObject(data) ;
            setBasicDataOfMovie();
            setTrailersMovie();
            setReviewsMovie() ;
            mProgressBar.setVisibility(View.INVISIBLE);
            mBinding.clMovieDetails.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onListItemReviewClicked(int clickedItemIndex, RecyclerviewReviewItemBinding itemBinding) {

        if(itemBinding.expandableLayout.isExpanded()){
            itemBinding.expandableLayout.collapse();
            itemBinding.ivArrowDropDown.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }else {
            itemBinding.expandableLayout.expand();
            itemBinding.ivArrowDropDown.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);

        }

    }

    public void openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onListItemTrailerClicked(int clickedItemIndex) {
        openWebPage(mTrailersList.get(clickedItemIndex)) ;
    }
}