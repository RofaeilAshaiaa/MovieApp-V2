package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MovieDetailsActivityBinding;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.movie_details_activity);
        setSupportActionBar(mBinding.tbMovieDetailsActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent= getIntent() ;
        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(getString(R.string.movie_id_string))) {
                int id = extras.getInt(getString(R.string.movie_id_string));

                Bundle bundle = new Bundle() ;
                bundle.putInt(getString(R.string.movie_id_string),id);

                Fragment mFragment = new MovieDetailsFragment();
                mFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details_fragment_container, mFragment)
                        .commit();
            }
        }

    }

}
