package idea.rofaeil.ashaiaa.myapplication;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import idea.rofaeil.ashaiaa.myapplication.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment;
    private MainActivityBinding mBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setClickListenerNavigationBottom();
    }

    private void setClickListenerNavigationBottom() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.favorites:
                        mFragment = new FavouriteFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.favorites));
                        break;
                    case R.id.top_rated:
                        mFragment = new TopRatedFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.top_rated));
                        break;
                    case R.id.popular:
                        mFragment = new PopularFragment();
                        mBinding.toolbar.setTitle(getResources().getString(R.string.popular));
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, mFragment)
                        .commit();
                return true;
            }
        });
    }

}
