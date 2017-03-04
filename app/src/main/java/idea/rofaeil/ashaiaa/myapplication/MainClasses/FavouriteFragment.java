package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.Movie;
import idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.RecyclerViewAdapter;
import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.FavouriteFragmentBinding;

public class FavouriteFragment extends Fragment {

    private FavouriteFragmentBinding mBinding;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false);

        return mBinding.getRoot();

    }
    @Override
    public void onStart() {
        super.onStart();
        mBinding.rvFavourite.setAdapter( new RecyclerViewAdapter(new ArrayList<Movie>(4) ,getContext() ,getActivity()));

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);

        mBinding.rvFavourite.setLayoutManager(mLayoutManager);
    }
}