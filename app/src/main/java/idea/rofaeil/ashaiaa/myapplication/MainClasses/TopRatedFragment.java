package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.TopRatedFragmentBinding;


public class TopRatedFragment extends Fragment {

    private TopRatedFragmentBinding mBinding;


    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.top_rated_fragment, container, false);

        return mBinding.getRoot();

    }

}
