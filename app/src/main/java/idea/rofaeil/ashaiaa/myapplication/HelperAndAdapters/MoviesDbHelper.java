package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MoviesReaderContract.*;

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static MoviesDbHelper sInstance;
    private static final String DATABASE_NAME = "movies.db" ;
    private static final int DATABASE_VERSION  = 1 ;

    private MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MoviesDbHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MoviesDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE "+
                MovieEntry.TABLE_NAME+" (" +
                MovieEntry.COLUMN_NAME_MovieId + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_NAME_OriginalTitle+ " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_MovieOverview+ " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_MovieRuntime+ " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_ReleaseDate+ " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_VoteAverage+ " TEXT NOT NULL," +
                MovieEntry.COLUMN_NAME_MoviePoster+ " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME );
        onCreate(db);
    }

    public static void addMovieToFavourites(Movie movie, SQLiteDatabase mDB, Context mContext){
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_NAME_MovieId , movie.getMovieId());
        cv.put(MovieEntry.COLUMN_NAME_MovieOverview , movie.getMovieOverview());
        cv.put(MovieEntry.COLUMN_NAME_MoviePoster , movie.getMoviePoster());
        cv.put(MovieEntry.COLUMN_NAME_MovieRuntime , movie.getRuntime());
        cv.put(MovieEntry.COLUMN_NAME_OriginalTitle , movie.getOriginalTitle());
        cv.put(MovieEntry.COLUMN_NAME_ReleaseDate , movie.getReleaseDate());
        cv.put(MovieEntry.COLUMN_NAME_VoteAverage , movie.getVoteAverage());
          mDB.insert(MovieEntry.TABLE_NAME,null,cv) ;
        Toast.makeText(mContext, "Movie Added to Favourites!", Toast.LENGTH_SHORT).show();
    }

    public static Cursor getAllMovies (SQLiteDatabase mDB){

        return mDB.query(
                MoviesReaderContract.MovieEntry.TABLE_NAME,// The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
    }

    public static Cursor isMovieExist (SQLiteDatabase mDB , int id){

        return mDB.query(
                MoviesReaderContract.MovieEntry.TABLE_NAME,// The table to query
                null,                               // The columns to return
                MovieEntry.COLUMN_NAME_MovieId+"= ?",   // The columns for the WHERE clause
                new String[]  { Integer.toString(id) }, // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                 // The sort order
        );

//
//        return mDB.rawQuery("SELECT * FROM "+MoviesReaderContract.MovieEntry.TABLE_NAME +
//                        "WHERE "+ MovieEntry.COLUMN_NAME_MovieId+"= ?"
//                ,new String[]  {Integer.toString(id) }) ;

    }
}
