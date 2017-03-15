package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MainRecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MoviesDbHelper;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.FavouriteFragmentBinding;

import static idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MoviesReaderContract.MovieEntry;

public class FavouriteFragment extends Fragment implements MainRecyclerViewAdapter.ListItemClickListener {

    private FavouriteFragmentBinding mBinding;
    private ArrayList<Movie> mMoviesList;
    private MainRecyclerViewAdapter mAdapter ;
    private Context mContext ;
    private FragmentActivity mMainActivity;
//    private ProgressBar mProgressBar;
    private SQLiteDatabase mDB;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false);
        mMainActivity= getActivity() ;
        mContext = getContext() ;
//        mProgressBar = (ProgressBar) mMainActivity.findViewById(R.id.progress_bar_main_activity);
        return mBinding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();

        mDB = MoviesDbHelper.getInstance(mContext).getReadableDatabase() ;
        Cursor cursor = MoviesDbHelper.getAllMovies(mDB) ;
        extractMovies(cursor);

        if(mMoviesList != null ){
            mAdapter = new MainRecyclerViewAdapter( mMoviesList ,mContext,this ) ;
            mBinding.rvFavourite.setAdapter(mAdapter );
            GridLayoutManager mLayoutManager = new GridLayoutManager( mContext,2);
            mBinding.rvFavourite.setLayoutManager(mLayoutManager);
        }else {
//            View rootView = mMainActivity.getWindow().getDecorView().findViewById(R.id.main_activity_layout);
//            View rootView = mMainActivity.getWindow().getDecorView().findViewById(android.R.id.content);
//            Snackbar.make(rootView
//                    , "R.string.snackbar_text_no_favourites", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            Toast.makeText(mContext, R.string.snackbar_text_no_favourites, Toast.LENGTH_SHORT).show();
        }

    }

    private void extractMovies(Cursor cursor) {

        int numberOfMovies = cursor.getCount();
        if(numberOfMovies == 0 ) return;
        mMoviesList = new ArrayList<>(numberOfMovies) ;
        cursor.moveToFirst() ;
        for (int i = 0; i < numberOfMovies; i++) {

            Movie movie = new Movie();
            movie.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_MovieId)));
            movie.setMovieOverview(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_MovieOverview)));
            movie.setMoviePoster(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_MoviePoster)));
            movie.setRuntime(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_MovieRuntime)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_OriginalTitle)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_ReleaseDate)));
            movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_VoteAverage)));
            mMoviesList.add(movie);
            cursor.moveToNext();
        }

    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        if ( MainActivity.isTwoPane ) {

            Bundle bundle = new Bundle() ;
            bundle.putParcelable(getString(R.string.movie_string_parcel), Parcels.wrap(mMoviesList.get(clickedItemIndex)) );

            Fragment mFragment = new FavouriteMovieDetailFragment();
            mFragment.setArguments(bundle);

            mMainActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container_details, mFragment)
                    .commit();

        } else {
            Intent intent = new Intent(mMainActivity, DetailFavouriteActivity.class);
            intent.putExtra(getString(R.string.movie_string_parcel), Parcels.wrap(mMoviesList.get(clickedItemIndex)));
            startActivity(intent);
        }
    }

}