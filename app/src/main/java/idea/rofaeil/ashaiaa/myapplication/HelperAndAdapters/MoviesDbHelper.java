package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters.MoviesReaderContract.*;

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db" ;
    private static final int DATABASE_VERSION  = 1 ;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE "+
                MovieEntry.TABLE_NAME+" (" +
                MovieEntry.COLUMN_NAME_MovieId + " INTEGER NOT NULL," +
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
}
