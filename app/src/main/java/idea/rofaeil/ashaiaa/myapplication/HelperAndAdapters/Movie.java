package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;


public class Movie {

    private int mMovieId;
    private String mMoviePoster;
    private String mMovieOverview;
    private String mReleaseDate;
    private String mMovieRuntime;
    private String mOriginalTitle;
    private String mVoteAverage;

    public Movie() {
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
