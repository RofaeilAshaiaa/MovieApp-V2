package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import idea.rofaeil.ashaiaa.myapplication.R;

/**
 * Created by Matrix on 3/15/2017.
 */

public class Utils {

    public static final String TAG = "mi5a";
    public static String[] URLs ;


    //check if network available and connected to one
    public static boolean isNetworkAvailable(FragmentActivity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void ConstructURLs(Context context) {
        String API_KEY = context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN);
        URLs = new String[2] ;
        URLs[0] = new String("http://api.themoviedb.org/3/movie/popular?api_key="+ API_KEY) ;
        URLs[1] = new String("http://api.themoviedb.org/3/movie/top_rated?api_key="+ API_KEY) ;

    }

    public static StringBuilder getStringBuilderOfDetailsURL(Context context,int Movie_ID){

        String API_KEY = context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN);
        return new StringBuilder("https://api.themoviedb.org/3/movie/")
                .append(Movie_ID)
                .append("?api_key=")
                .append(API_KEY)
                .append("&append_to_response=videos,reviews");
    }

    public static void inflateOfflineLayout(Context context,FrameLayout frameLayout){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.offline_layout,frameLayout,false);
        frameLayout.removeAllViewsInLayout();
        frameLayout.addView(view);
    }
}
