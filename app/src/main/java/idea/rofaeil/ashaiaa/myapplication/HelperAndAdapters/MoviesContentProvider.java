package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Matrix on 3/17/2017.
 */

public class MoviesContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 102;
    private static final UriMatcher sUriMatcher = buildUriMatcher() ;
    private MoviesDbHelper mMoviesDbHelper;

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesReaderContract.AUTHORITY,MoviesReaderContract.PATH_MOVIES,MOVIES);
        uriMatcher.addURI(MoviesReaderContract.AUTHORITY,MoviesReaderContract.PATH_MOVIES+"/*", MOVIES_WITH_ID);
//        sUriMatcher.equals(uriMatcher);
        return  uriMatcher ;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMoviesDbHelper = MoviesDbHelper.getInstance(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mMoviesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor =null;
        switch (match){

            case MOVIES:
                returnCursor = db.query(
                        MoviesReaderContract.MovieEntry.TABLE_NAME,// The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );
                break;

            case MOVIES_WITH_ID:


                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri.toString());

        }

        returnCursor.setNotificationUri( getContext().getContentResolver(),uri);

        return returnCursor  ;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri =null;
        switch (match){

            case MOVIES:
               long id = db.insert(MoviesReaderContract.MovieEntry.TABLE_NAME, null, values);
                if(id>0){
                  returnUri =ContentUris.withAppendedId(MoviesReaderContract.MovieEntry.CONTENT_URI, id);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri.toString());

        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        return db.delete(MoviesReaderContract.MovieEntry.TABLE_NAME, selection, selectionArgs);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
