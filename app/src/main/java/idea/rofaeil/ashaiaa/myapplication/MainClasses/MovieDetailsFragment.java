package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.ContentProvider.MoviesReaderContract;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.AsyncTaskLoaderContentProviderQuery;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.NetworkAsyncTaskLoader;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.ReviewsRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.TraitorsRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Utils;
import idea.rofaeil.ashaiaa.myapplication.Objects.Movie;
import idea.rofaeil.ashaiaa.myapplication.Objects.Review;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MovieDetailsFragmentBinding;
import idea.rofaeil.ashaiaa.myapplication.databinding.RecyclerviewReviewItemBinding;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
        , TraitorsRecyclerViewAdapter.ListItemClickListener, ReviewsRecyclerViewAdapter.ListItemClickListener {

    private final int Movie_DETAILS_LOADER_ID = 33;
    private MovieDetailsFragmentBinding mBinding;
    private ProgressBar mProgressBar;
    private JSONObject response;
    private Context mContext;
    private FragmentActivity mParentActivity;
    private StringBuilder URL;
    private int Movie_ID;
    private boolean isFavorite;
    private Movie mMovie;
    private Toast mToast ;
    private ArrayList<String> mTrailersList;
    private ArrayList<Review> mReviewList;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Movie_ID = getArguments().getInt(getString(R.string.movie_id_string));
        mBinding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
        mParentActivity = getActivity();
        mContext = getContext();

        mProgressBar = mBinding.pbMovieDetailsFragment;
        FrameLayout frameLayout = (FrameLayout) mParentActivity.findViewById(R.id.details_fragment_container);

        if(Utils.isNetworkAvailable(mParentActivity)){
            frameLayout.removeAllViewsInLayout();
            makeNetworkRequest();
        }else{
            if(frameLayout!=null)
            Utils.inflateOfflineLayout(mContext,frameLayout);
        }
        setOnClickListenerFavouriteMeImage();
        return mBinding.getRoot();
    }

    private void ChangeImageOfFavouriteMeIcon() {


        AsyncTaskLoaderContentProviderQuery loader = new AsyncTaskLoaderContentProviderQuery(
                mContext,
                "query",
                null,
                MoviesReaderContract.MovieEntry.COLUMN_NAME_OriginalTitle+" =?",
                new String[] {mMovie.getOriginalTitle()},
                null){
            @Override
            public void deliverResult(Object object) {
                 Cursor cursor = (Cursor) object ;
                super.deliverResult(cursor);
                if (cursor.getCount() >= 1) {
                    mBinding.ivAddToFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
                    isFavorite = true;
                } else {
                    cursor.close();
                }
                cursor.close();
            }
        };
        loader.forceLoad();
    }

    private void setOnClickListenerFavouriteMeImage() {
        mBinding.ivAddToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite != true) {
                    ContentValues cv = new ContentValues();
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_MovieId, mMovie.getMovieId());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_MovieOverview, mMovie.getMovieOverview());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_MoviePoster, mMovie.getMoviePoster());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_MovieRuntime, mMovie.getRuntime());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_OriginalTitle, mMovie.getOriginalTitle());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_ReleaseDate, mMovie.getReleaseDate());
                    cv.put(MoviesReaderContract.MovieEntry.COLUMN_NAME_VoteAverage, mMovie.getVoteAverage());
                    try {
                        Uri uri = mContext.getContentResolver().insert(MoviesReaderContract.MovieEntry.CONTENT_URI, cv);
                        if(uri != null){
                            isFavorite =true;
                            mBinding.ivAddToFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
                            Toast.makeText(mContext, "Movie Added to Favourites!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, R.string.some_thing_wrong, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    int deletedRows =mContext.getContentResolver().
                            delete(
                                    MoviesReaderContract.MovieEntry.CONTENT_URI,
                                    MoviesReaderContract.MovieEntry.COLUMN_NAME_OriginalTitle + " =?",
                                    new String[]{ mMovie.getOriginalTitle()});
                    if(deletedRows >= 1){
                        mBinding.ivAddToFavourite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                        isFavorite =false ;
                        Toast.makeText(mContext, "Movie Removed from Favourites!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext,R.string.some_thing_wrong, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void makeNetworkRequest() {

        URL = Utils.getStringBuilderOfDetailsURL(mContext, Movie_ID);

        mProgressBar.setVisibility(View.VISIBLE);
        mBinding.clMovieDetails.setVisibility(View.INVISIBLE);
        LoaderManager loaderManager = mParentActivity.getSupportLoaderManager();
        Loader<String> popularLoader = loaderManager.getLoader(Movie_DETAILS_LOADER_ID);

        if(popularLoader == null)
        {
          loaderManager.initLoader(Movie_DETAILS_LOADER_ID,null,this).forceLoad();
        }else {
            loaderManager.restartLoader(Movie_DETAILS_LOADER_ID,null,this).forceLoad();
        }
    }

    private void setBasicDataOfMovie() throws JSONException {
        mMovie = new Movie();
        mMovie.setMovieId(Movie_ID);
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
        if (MainActivity.isTwoPane) {
            TextView textView = (TextView) mParentActivity.findViewById(R.id.tv_movie_name_container);
            textView.setText(originalTitle);
        } else {
            Toolbar toolbar = (Toolbar) mParentActivity.findViewById(R.id.tb_movie_details_activity);
            toolbar.setTitle(originalTitle);
        }

        String releaseDate = response.getString("release_date");
        mMovie.setReleaseDate(releaseDate);
        mBinding.tvRealseDate.setText(releaseDate);

        String runtime = response.getString("runtime");
        mMovie.setRuntime(runtime);
        mBinding.tvMovieDuration.setText(runtime + "min.");

        double vote_average = response.getDouble("vote_average");
        float rating = (float) vote_average / 2;
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

        if (numberOfTrailers != 0) {

            mBinding.rvTrailors.setAdapter(new TraitorsRecyclerViewAdapter(mTrailersList, mContext, this));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
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
            Review review = new Review(reviewAuthor, reviewContent);
            mReviewList.add(review);
        }

        if (numberOfReviews != 0) {
            mBinding.rvReviews.setAdapter(new ReviewsRecyclerViewAdapter(mReviewList, mContext, this));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
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
            response = new JSONObject(data);
            setBasicDataOfMovie();
            setTrailersMovie();
            setReviewsMovie();
            ChangeImageOfFavouriteMeIcon();
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

        if (itemBinding.expandableLayout.isExpanded()) {
            itemBinding.expandableLayout.collapse();
            itemBinding.ivArrowDropDown.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else {
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
        openWebPage(mTrailersList.get(clickedItemIndex));
    }
}