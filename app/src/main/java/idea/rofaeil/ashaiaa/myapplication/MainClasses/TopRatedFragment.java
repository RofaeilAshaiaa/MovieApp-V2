package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MainRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.NetworkAsyncTaskLoader;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Utils;
import idea.rofaeil.ashaiaa.myapplication.Objects.Movie;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.TopRatedFragmentBinding;

import static idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Utils.URLs;

public class TopRatedFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
        ,MainRecyclerViewAdapter.ListItemClickListener {

    private final int TOP_RATED_LOADER_ID = 22;
    private TopRatedFragmentBinding mBinding;
    private ProgressBar mProgressBar ;
    private Context mContext ;
    private Parcelable mLayoutManagerSavedState ;
    private GridLayoutManager mLayoutManager ;
    private ArrayList<Movie> mMoviesList ;
    private FragmentActivity mMainActivity;


    public TopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.top_rated_fragment, container, false);
        mMainActivity = getActivity() ;
        mContext = getContext() ;
        mProgressBar = (ProgressBar) mMainActivity.findViewById(R.id.progress_bar_main_activity);
        FrameLayout frameLayout = (FrameLayout) mMainActivity.findViewById(R.id.main_container);
        if(Utils.isNetworkAvailable(mMainActivity)){
            frameLayout.removeAllViewsInLayout();
            makeNetworkRequest();
        }else{
            Utils.inflateOfflineLayout(mContext,frameLayout);
        }
        return mBinding.getRoot();

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new NetworkAsyncTaskLoader(getContext() ,URLs[1]);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mScrolledPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
//        outState.putInt(mMainActivity.getResources().getString(R.string.recycler_parcelable),mScrolledPosition);
//        outState.putParcelable(mMainActivity.getResources().getString(R.string.list_movies_parceler),Parcels.wrap(mMoviesList));
        outState.putParcelable(mMainActivity.getResources()
                        .getString(R.string.recycler_parcelable)
                , mLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null ) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable(
                    mMainActivity.getResources().getString
                            (R.string.recycler_parcelable));

        }
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        JSONObject mJsonObject;
        try {
            mJsonObject = new JSONObject(data);
            JSONArray mMoviesJsonArray = mJsonObject.getJSONArray("results");
            mMoviesList = Movie.extractMovieDataFromJson(mMoviesJsonArray);
            mBinding.rvTopRated.setAdapter( new MainRecyclerViewAdapter(mMoviesList,getContext() ,this));
            mLayoutManager = new GridLayoutManager(getContext(),2);
            mBinding.rvTopRated.setLayoutManager(mLayoutManager);
            scrollToTargetPosition();
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

        if(popularLoader == null)
        {
          loaderManager.initLoader(TOP_RATED_LOADER_ID,null,this).forceLoad();
        }else {
            loaderManager.restartLoader(TOP_RATED_LOADER_ID,null,this).forceLoad();
        }

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        if (MainActivity.isTwoPane) {

            Bundle bundle = new Bundle() ;
            bundle.putInt(getString(R.string.movie_id_string),mMoviesList.get(clickedItemIndex).getMovieId());

            Fragment mFragment = new MovieDetailsFragment();
            mFragment.setArguments(bundle);

            mMainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container_details , mFragment)
                    .commit();

        } else {
            Intent intent = new Intent(mMainActivity, MovieDetailsActivity.class);
            intent.putExtra(getString(R.string.movie_id_string), mMoviesList.get(clickedItemIndex).getMovieId());
            startActivity(intent);
        }
    }

    private void scrollToTargetPosition() {
//        if(mScrolledPosition != -1)
////            mBinding.rvPopular.getLayoutManager().scrollToPosition(mScrolledPosition);
//        mLayoutManager.scrollToPositionWithOffset(mScrolledPosition,0);
        if (mLayoutManagerSavedState != null) {
            mLayoutManager.
                    onRestoreInstanceState(mLayoutManagerSavedState);
        }
    }

}
