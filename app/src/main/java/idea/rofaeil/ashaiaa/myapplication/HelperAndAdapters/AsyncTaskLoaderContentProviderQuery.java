package idea.rofaeil.ashaiaa.myapplication.HelperAndAdapters;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import idea.rofaeil.ashaiaa.myapplication.ContentProvider.MoviesReaderContract;

/**
 * Created by Matrix on 3/18/2017.
 */

public class AsyncTaskLoaderContentProviderQuery extends AsyncTaskLoader<Object> {

    private Context mContext;
    private String[] projection;
    private String[] selectionArgs;
    private String selection;
    private String sortOrder;
    private String type;

    public AsyncTaskLoaderContentProviderQuery(Context mContext, String type, String[] projection,
                                               String selection, String[] selectionArgs, String sortOrder) {
        super(mContext);
        this.mContext = mContext;
        this.projection = projection;
        this.selectionArgs = selectionArgs;
        this.selection = selection;
        this.sortOrder = sortOrder;
        this.type = type;
    }

    @Override
    public Object loadInBackground() {

        Object cursor  =null;
        try {
            switch (type) {
                case "query":
                    cursor = mContext.getContentResolver().
                            query(MoviesReaderContract.MovieEntry.CONTENT_URI
                                    , projection
                                    , selection
                                    , selectionArgs
                                    , sortOrder);
                    break;

                case "delete":

                    break;

                case "insert" :

                    break;

            }
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            return cursor;
        }

        return cursor;
    }

}
