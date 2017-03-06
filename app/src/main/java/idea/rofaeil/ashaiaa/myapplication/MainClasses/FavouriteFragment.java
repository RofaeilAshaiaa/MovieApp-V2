package idea.rofaeil.ashaiaa.myapplication.MainClasses;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import idea.rofaeil.ashaiaa.myapplication.R;
import idea.rofaeil.ashaiaa.myapplication.databinding.FavouriteFragmentBinding;

public class FavouriteFragment extends Fragment {

    private final int TOP_RATED_LOADER_ID = 33;
    private FavouriteFragmentBinding mBinding;
    private ProgressBar mProgressBar;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.favourite_fragment, container, false);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar_review_subjects);
        return mBinding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
//        mBinding.rvFavourite.setAdapter( new RecyclerViewAdapter(new ArrayList<Movie>(4) ,getContext() ));
//
//        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
//
//        mBinding.rvFavourite.setLayoutManager(mLayoutManager);
    }
}