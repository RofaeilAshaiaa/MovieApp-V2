package idea.rofaeil.ashaiaa.myapplication;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import idea.rofaeil.ashaiaa.myapplication.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigation;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private MainActivityBinding mBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.favorites:
                mFragment = new FavouriteFragment();
                break;
            case R.id.top_rated:
                mFragment = new TopRatedFragment();
                break;
            case R.id.popular:
                mFragment = new PopularFragment();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(mBinding.mainContainer.getId(), mFragment)
                .commit();
//        final FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_container, fragment).commit();
        return true;
    }
}
