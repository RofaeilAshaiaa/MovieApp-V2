package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.provider.BaseColumns;

public final class MoviesReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private MoviesReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {

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
