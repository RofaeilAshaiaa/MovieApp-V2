package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.content.ContentValues;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import idea.rofaeil.ashaiaa.myapplication.ContentProvider.MoviesReaderContract;
import idea.rofaeil.ashaiaa.myapplication.Objects.Movie;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.FavouriteMovieDetailFragmentBinding;

public class FavouriteMovieDetailFragment extends Fragment {

    private FavouriteMovieDetailFragmentBinding mBinding;
    private Movie mMovie;
    private FragmentActivity mParentActivity;
    private boolean isFavorite = true;
    private Context mContext ;

    public FavouriteMovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mMovie = Parcels.unwrap(getArguments().getParcelable(getString(R.string.movie_string_parcel)));
        mBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_movie_detail_fragment, container, false);
        mParentActivity = getActivity();
        mContext= getContext() ;

        Picasso.with(getActivity().getBaseContext()).load(mMovie.getMoviePoster())
                .error(R.drawable.ic_error_outline_black_24dp).fit()
                .tag(getActivity().getBaseContext()).into(mBinding.ivMoviePosterFavourite);

        mBinding.ivAddToFavouriteInFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
        mBinding.tvMovieDurationFavourite.setText(mMovie.getRuntime());
        mBinding.tvOverviewOfMovieFavourite.setText(mMovie.MovieOverview);
        mBinding.rbMovieRatingFavourite.setRating(Float.parseFloat(mMovie.getVoteAverage()));
        mBinding.tvRealseDateFavourite.setText(mMovie.getReleaseDate());

        if (MainActivity.isTwoPane) {
            TextView textView = (TextView) mParentActivity.findViewById(R.id.tv_movie_name_container);
            textView.setText(mMovie.getOriginalTitle());
        } else {
            Toolbar toolbar = (Toolbar) mParentActivity.findViewById(R.id.tb_movie_details_activity);
            toolbar.setTitle(mMovie.getOriginalTitle());
        }

        setOnClickListenerFavouriteMeImage();

        return mBinding.getRoot();
    }

    private void setOnClickListenerFavouriteMeImage() {
        mBinding.ivAddToFavouriteInFavourite.setOnClickListener(new View.OnClickListener() {
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
                            mBinding.ivAddToFavouriteInFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
                            Toast.makeText(mContext, "Movie Added to Favourites!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, R.string.some_thing_wrong, Toast.LENGTH_SHORT).show();
                    }
                } else {


                    int deletedRows = mContext.getContentResolver().
                        delete(
                                MoviesReaderContract.MovieEntry.CONTENT_URI,
                                MoviesReaderContract.MovieEntry.COLUMN_NAME_OriginalTitle + " =?",
                                new String[]{mMovie.getOriginalTitle()});
                if (deletedRows >= 1) {
                    mBinding.ivAddToFavouriteInFavourite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                    isFavorite =false;
                    Toast.makeText(mContext, "Movie Removed from Favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.some_thing_wrong, Toast.LENGTH_SHORT).show();
                }
            }
            }
        });
    }

}
