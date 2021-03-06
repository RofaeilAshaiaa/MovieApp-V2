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
import idea.rofaeil.ashaiaa.myapplication.databinding.PopularFragmentBinding;

import static idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Utils.URLs;

public class PopularFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
        , MainRecyclerViewAdapter.ListItemClickListener {

    private final int POPULAR_LOADER_ID = 11;
    ArrayList<Movie> mMoviesList;
    private PopularFragmentBinding mBinding;
    private ProgressBar mProgressBar;
    private Context mContext ;
    private Parcelable mLayoutManagerSavedState ;
    private GridLayoutManager mLayoutManager ;
    private FragmentActivity mMainActivity;

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.popular_fragment, container, false);
        mMainActivity = getActivity();
        mContext = getContext() ;
        mProgressBar = (ProgressBar) mMainActivity.findViewById(R.id.progress_bar_main_activity);
        FrameLayout frameLayout = (FrameLayout) mMainActivity.findViewById(R.id.main_container);
        if(Utils.isNetworkAvailable(mMainActivity)){

//            if(savedInstanceState != null ) {
////                mScrolledPosition =savedInstanceState.getInt(
////                        mMainActivity.getResources().getString(R.string.recycler_parcelable),-1);
////                mMoviesList = Parcels.unwrap(savedInstanceState.getParcelable(
////                        mMainActivity.getResources().getString(R.string.list_movies_parceler)));
////                setAdapterRecyclerView();
//                mLayoutManagerSavedState = savedInstanceState.getParcelable(
//                        mMainActivity.getResources().getString
//                                (R.string.recycler_parcelable));
//
//            }
//            else{
            frameLayout.removeAllViewsInLayout();
            makeNetworkRequest();
//            }
        }else{
            Utils.inflateOfflineLayout(mContext,frameLayout);
        }
        return mBinding.getRoot();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mScrolledPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
//        outState.putInt(mMainActivity.getResources().getString(R.string.recycler_parcelable),mScrolledPosition);
//        outState.putParcelable(mMainActivity.getResources().getString(R.string.list_movies_parceler),Parcels.wrap(mMoviesList));
     if(mLayoutManager!=null)
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

    private void makeNetworkRequest() {

        mProgressBar.setVisibility(View.VISIBLE);
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<String> popularLoader = loaderManager.getLoader(POPULAR_LOADER_ID);

        if(popularLoader == null)
        {
          loaderManager.initLoader(POPULAR_LOADER_ID,null,this).forceLoad();
        }else {
            loaderManager.restartLoader(POPULAR_LOADER_ID,null,this).forceLoad();
        }

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new NetworkAsyncTaskLoader(mContext, URLs[0]);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        JSONObject mJsonObject;
        try {
            mJsonObject = new JSONObject(data);
            JSONArray mMoviesJsonArray = mJsonObject.getJSONArray("results");
            mMoviesList = Movie.extractMovieDataFromJson(mMoviesJsonArray);
            setAdapterRecyclerView();
            scrollToTargetPosition();
        } catch (JSONException e) {
        }

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void setAdapterRecyclerView() {
        mBinding.rvPopular.setAdapter(new MainRecyclerViewAdapter(mMoviesList, getContext(), this));
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mBinding.rvPopular.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

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
}