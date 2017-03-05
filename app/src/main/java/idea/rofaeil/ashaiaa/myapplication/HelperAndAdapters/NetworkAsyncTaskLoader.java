package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkAsyncTaskLoader extends AsyncTaskLoader<String> {
    private String URL;

    public NetworkAsyncTaskLoader(Context context, String url) {
        super(context);
        URL = url;
    }

    @Override
    public String loadInBackground() {

        OkHttpClient client = new OkHttpClient();

        String mJsonResponse;

        Request request = new Request.Builder().url(URL).build();

        try {
            Response response = client.newCall(request).execute();
            mJsonResponse = response.body().string();
        } catch (IOException e) {

            return null;
        }

        return mJsonResponse;
    }
}
