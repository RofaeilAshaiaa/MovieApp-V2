package idea.rofaeil.ashaiaa.myapplication.MainClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import idea.rofaeil.ashaiaa.myapplication.R;

public class MovieDetailsFragment extends Fragment {


    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_details_fragment, container, false);
    }

}
