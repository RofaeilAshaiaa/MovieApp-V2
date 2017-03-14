package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.DetailFavouriteActivityBinding;

public class DetailFavouriteActivity extends AppCompatActivity {

    private DetailFavouriteActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.detail_favourite_activity);
        setSupportActionBar(mBinding.tbMovieDetailsActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(getString(R.string.movie_string_parcel)));

        Bundle bundle = new Bundle() ;
        bundle.putParcelable(getString(R.string.movie_string_parcel), Parcels.wrap(movie) );

        Fragment mFragment = new FavouriteMovieDetailFragment();
        mFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details_fragment_container_favourites, mFragment)
                .commit();
    }
}
