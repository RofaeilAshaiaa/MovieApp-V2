package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Utils;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    public static int targetY;
    public static boolean isTwoPane ;
    private int lastSelected;
    private Fragment mFragment;
    private MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        if (savedInstanceState == null) {
            setLastSelectedFragment();
        }else{
            setLastSelectedPageOfBottomNavigation();
        }
        setClickListenerNavigationBottom();
        setImageHeightValue();
        Utils.ConstructURLs(this);
        isTwoPane = isTwoPane() ;
    }

    private void setLastSelectedPageOfBottomNavigation() {

        setLastSelectedValue();

        switch (lastSelected) {
            case 3:
                mBinding.toolbar.setTitle(getResources().getString(R.string.favorites));
                mBinding.bottomNavigation.getMenu().getItem(2).setChecked(true);
                break;
            case 2:
                mBinding.toolbar.setTitle(getResources().getString(R.string.top_rated));
                mBinding.bottomNavigation.getMenu().getItem(1).setChecked(true);
                break;
            case 1:
                mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                mBinding.bottomNavigation.getMenu().getItem(0).setChecked(true);
                break;

            default:
                mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                mBinding.bottomNavigation.getMenu().getItem(0).setChecked(true);
                break;

        }

    }

    private void setLastSelectedValue() {
        SharedPreferences mSharedPref = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        lastSelected = mSharedPref.getInt(getString(R.string.last_selected), 4);
    }

    private void setLastSelectedFragment() {
        setLastSelectedValue();

            switch (lastSelected) {
                case 3:
                    mFragment = new FavouriteFragment();
                    mBinding.toolbar.setTitle(getResources().getString(R.string.favorites));
                    mBinding.bottomNavigation.getMenu().getItem(2).setChecked(true);
                    break;
                case 2:
                    mFragment = new TopRatedFragment();
                    mBinding.toolbar.setTitle(getResources().getString(R.string.top_rated));
                    mBinding.bottomNavigation.getMenu().getItem(1).setChecked(true);
                    break;
                case 1:
                    mFragment = new PopularFragment();
                    mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                    mBinding.bottomNavigation.getMenu().getItem(0).setChecked(true);
                    break;

                default:
                    mFragment = new PopularFragment();
                    mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                    mBinding.bottomNavigation.getMenu().getItem(0).setChecked(true);
                    break;

            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, mFragment)
                    .commit();
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

    private boolean isTwoPane() {

//        View view = mBinding.mainContainerDetails ;
//        if (findViewById(R.id.main_container_details) != null )return true;
//        if ( view != null )return true;
        if (  mBinding.mainContainerDetails != null )return true;
        else return false;
    }

}
