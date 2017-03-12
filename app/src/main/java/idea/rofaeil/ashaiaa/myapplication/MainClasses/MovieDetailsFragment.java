package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MainRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.NetworkAsyncTaskLoader;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.TraitorsRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MovieDetailsFragmentBinding;

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>,TraitorsRecyclerViewAdapter.ListItemClickListener {


    private final int TOP_RATED_LOADER_ID = 33;
    private MovieDetailsFragmentBinding mBinding;
    private ProgressBar mProgressBar;
    private JSONObject response ;
    private Context mContext ;
    private FragmentActivity mDetailActivity;
    private StringBuilder URL;
    private int Movie_ID;
    private ArrayList<String> mTrailersList ;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Movie_ID = getArguments().getInt("id");

        mBinding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
        mDetailActivity = getActivity();
        mContext = getContext() ;
        mProgressBar =  mBinding.pbMovieDetailsFragment;
        makeNetworkRequest();
        return mBinding.getRoot();
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

        String poster_path = response.getString("poster_path");
        StringBuilder base_url = new StringBuilder("http://image.tmdb.org/t/p/");
        base_url.append("w185/").append(poster_path);
        String url = base_url.toString();

            Picasso.with(getActivity().getBaseContext()).load(url)
                    .error(R.drawable.ic_error_outline_black_24dp).fit()
                    .tag(getActivity().getBaseContext()).into(mBinding.ivMoviePoster);

        String original_title = response.getString("original_title");
        if(true){
            Toolbar toolbar = (Toolbar) mDetailActivity.findViewById(R.id.tb_movie_details_activity);
            toolbar.setTitle(original_title);

        }else {

        }

        String release_date = response.getString("release_date");
        mBinding.tvRealseDate.setText(release_date);

        String runtime = response.getString("runtime");
        mBinding.tvMovieDuration.setText(runtime + "min.");

        double vote_average = response.getDouble("vote_average");
        float rating =(float) vote_average/2 ;
        mBinding.rbMovieRating.setRating(rating);

        String overview = response.getString("overview");
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
    public void onListItemTrailerClicked(int clickedItemIndex) {

    }
}
