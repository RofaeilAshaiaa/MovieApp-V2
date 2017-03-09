package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    public static final String[] URLs =
            {"http://api.themoviedb.org/3/movie/popular?api_key=27c5319da038dffe1e6957609d9797a0",
                    "http://api.themoviedb.org/3/movie/top_rated?api_key=27c5319da038dffe1e6957609d9797a0"};
    public static int targetY;
    private Fragment mFragment;
    private MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setClickListenerNavigationBottom();
        setImageHeightValue();

//        SharedPreferences mSharedPref = getApplicationContext()
//                .getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
//        int last_selected = mSharedPref.getInt(getString(R.string.last_selected), 199);

    }

    private void setImageHeightValue() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int heightPixels = displaymetrics.heightPixels;
        targetY = 2 * (heightPixels / 5);
    }

    private void setClickListenerNavigationBottom() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences
                (getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        final SharedPreferences.Editor mySharedPreferencesEditor = sharedPreferences.edit();

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.favorites:
                        mySharedPreferencesEditor.putInt(getString(R.string.last_selected), 3);
                        mFragment = new FavouriteFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.favorites));
                        break;
                    case R.id.top_rated:
                        mySharedPreferencesEditor.putInt(getString(R.string.last_selected), 2);
                        mFragment = new TopRatedFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.top_rated));
                        break;
                    case R.id.popular:
                        mySharedPreferencesEditor.putInt(getString(R.string.last_selected), 1);
                        mFragment = new PopularFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                        break;
                }

                mySharedPreferencesEditor.apply();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, mFragment)
                        .commit();
                return true;
            }
        });
    }

    public static boolean isTwoPane(FragmentActivity activity ) {

//        activity.findViewById()

        return false;
    }

}
