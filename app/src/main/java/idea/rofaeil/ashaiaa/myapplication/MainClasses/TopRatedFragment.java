package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.NetworkAsyncTaskLoader;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.RecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.TopRatedFragmentBinding;

public class TopRatedFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
        ,RecyclerViewAdapter.ListItemClickListener {

    private final int TOP_RATED_LOADER_ID = 22;
    private TopRatedFragmentBinding mBinding;
    private ProgressBar mProgressBar ;
    private ArrayList<Movie> mMoviesList ;
    private FragmentActivity mMainActivity;


    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.top_rated_fragment, container, false);
        mMainActivity = getActivity() ;
        mProgressBar = (ProgressBar) mMainActivity.findViewById(R.id.progress_bar_review_subjects);
        makeNetworkRequest();
        return mBinding.getRoot();

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new NetworkAsyncTaskLoader(getContext() ,MainActivity.URLs[1]);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        JSONObject mJsonObject;
        try {
            mJsonObject = new JSONObject(data);
            JSONArray mMoviesJsonArray = mJsonObject.getJSONArray("results");
             mMoviesList = Movie.extractMovieDataFromJson(mMoviesJsonArray);
            mBinding.rvTopRated.setAdapter( new RecyclerViewAdapter(mMoviesList,getContext() ,this));
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
            mBinding.rvTopRated.setLayoutManager(mLayoutManager);
        } catch (JSONException e) {
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void makeNetworkRequest() {

        mProgressBar.setVisibility(View.VISIBLE);
        LoaderManager loaderManager = mMainActivity.getSupportLoaderManager() ;
        Loader<String> popularLoader = loaderManager.getLoader(TOP_RATED_LOADER_ID);
        loaderManager.initLoader(TOP_RATED_LOADER_ID,null,this).forceLoad();

//        if(popularLoader == null)
//        {
//          loaderManager.initLoader(POPULAR_LOADER_ID,null,this).forceLoad();
//        }else {
//            loaderManager.restartLoader(POPULAR_LOADER_ID,null,this).startLoading();
//        }

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        if (MainActivity.isTwoPane(mMainActivity)) {

        } else {
            Intent intent = new Intent(mMainActivity, MovieDetailsActivity.class);
            intent.putExtra(getString(R.string.movie_id_string), mMoviesList.get(clickedItemIndex).getMovieId());
            startActivity(intent);
        }
    }
}
