package idea.rofaeil.ashaiaa.myapplication.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Movie {

    public int MovieId;
    public String MoviePoster;
    public String MovieOverview;
    public String ReleaseDate;
    public String MovieRuntime;
    public String OriginalTitle;
    public String VoteAverage;

    public Movie() {
    }

    public static ArrayList<Movie> extractMovieDataFromJson(JSONArray movie_jsonArray) throws JSONException {
        int ctr = movie_jsonArray.length();
        ArrayList<Movie> moviesListInMethod = new ArrayList<>(ctr);

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
        return MoviePoster;
    }

    public void setMoviePoster(String mMoviePoster) {
        this.MoviePoster = mMoviePoster;
    }

    public String getRuntime() {
        return MovieRuntime;
    }

    public void setRuntime(String runtime) {
        this.MovieRuntime = runtime;
    }

    public String getMovieOverview() {
        return MovieOverview;
    }

    public void setMovieOverview(String mMovieOverview) {
        this.MovieOverview = mMovieOverview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.ReleaseDate = mReleaseDate;
    }

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int mMovieId) {
        this.MovieId = mMovieId;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.OriginalTitle = mOriginalTitle;
    }

    public String getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(String mVoteAverage) {
        this.VoteAverage = mVoteAverage;
    }
}
