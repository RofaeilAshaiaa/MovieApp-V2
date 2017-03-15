package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.FavouriteMovieDetailFragmentBinding;

public class FavouriteMovieDetailFragment extends Fragment {

    private FavouriteMovieDetailFragmentBinding mBinding;

    private FragmentActivity mParentActivity;

    public FavouriteMovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Movie movie = Parcels.unwrap(getArguments().getParcelable(getString(R.string.movie_string_parcel)));
        mBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_movie_detail_fragment, container, false);
        mParentActivity = getActivity();

        Picasso.with(getActivity().getBaseContext()).load(movie.getMoviePoster())
                .error(R.drawable.ic_error_outline_black_24dp).fit()
                .tag(getActivity().getBaseContext()).into(mBinding.ivMoviePosterFavourite);

        mBinding.ivAddToFavouriteInFavourite.setImageResource(R.drawable.ic_favorite_green_24dp);
        mBinding.tvMovieDurationFavourite.setText(movie.getRuntime());
        mBinding.tvOverviewOfMovieFavourite.setText(movie.MovieOverview);
        mBinding.rbMovieRatingFavourite.setRating(Float.parseFloat(movie.getVoteAverage()));
        mBinding.tvRealseDateFavourite.setText(movie.getReleaseDate());

        if (MainActivity.isTwoPane) {
            TextView textView = (TextView) mParentActivity.findViewById(R.id.tv_movie_name_container);
            textView.setText(movie.getOriginalTitle());
        } else {
            Toolbar toolbar = (Toolbar) mParentActivity.findViewById(R.id.tb_movie_details_activity);
            toolbar.setTitle(movie.getOriginalTitle());
        }

        return mBinding.getRoot();
    }

}
