package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MoviesReaderContract {

    public static final String AUTHORITY = "idea.rofaeil.ashaiaa.myapplication" ;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = MovieEntry.TABLE_NAME ;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private MoviesReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {

        // content://idea.rofaeil.ashaiaa.myapplication/movies
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build() ;

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MoviePoster = "moviePoster";
        public static final String COLUMN_NAME_MovieId = "movieId";
        public static final String COLUMN_NAME_MovieOverview = "movieOverview";
        public static final String COLUMN_NAME_ReleaseDate = "releaseDate";
        public static final String COLUMN_NAME_MovieRuntime = "movieRuntime";
        public static final String COLUMN_NAME_OriginalTitle = "originalTitle";
        public static final String COLUMN_NAME_VoteAverage = "voteAverage";

    }
}
