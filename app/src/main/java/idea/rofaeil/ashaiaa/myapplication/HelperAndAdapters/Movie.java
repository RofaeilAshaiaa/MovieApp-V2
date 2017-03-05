package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Movie {

    public int mMovieId;
    public String mMoviePoster;
    public String mMovieOverview;
    public String mReleaseDate;
    public String mMovieRuntime;
    public String mOriginalTitle;
    public String mVoteAverage;

    public Movie() {
    }

    public static ArrayList<Movie> extractMovieDataFromJson(JSONArray movie_jsonArray) throws JSONException {
        int ctr = movie_jsonArray.length();
        ArrayList<Movie> moviesListInMethod = new ArrayList<>(ctr);
        //ArrayList<Movie> movies_ArrayList_in_method = new ArrayList<>();

        for (int i = 0; i < ctr; i++) {
            Movie mMovie = new Movie();

            JSONObject jsonObject_of_movie = movie_jsonArray.getJSONObject(i);

            StringBuilder URI = new StringBuilder("http://image.tmdb.org/t/p/");
            URI.append("w185/").append(jsonObject_of_movie.getString("poster_path"));

            mMovie.setMoviePoster(URI.toString());
            mMovie.setMovieId(jsonObject_of_movie.getInt("id"));
            moviesListInMethod.add(mMovie);
        }
        return moviesListInMethod;
    }

    public String getMoviePoster() {
        return mMoviePoster;
    }

    public void setMoviePoster(String mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }

    public String getRuntime() {
        return mMovieRuntime;
    }

    public void setRuntime(String runtime) {
        this.mMovieRuntime = runtime;
    }

    public String getMovieOverview() {
        return mMovieOverview;
    }

    public void setMovieOverview(String mMovieOverview) {
        this.mMovieOverview = mMovieOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }
}
