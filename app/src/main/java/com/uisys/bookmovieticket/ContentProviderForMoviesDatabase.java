package com.uisys.bookmovieticket;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class ContentProviderForMoviesDatabase extends ContentProvider {
    MovieDatabase movieDatabase;
    public ContentProviderForMoviesDatabase() {
    }

    static UriMatcher uriMatcher= new UriMatcher(-1);
    {

        uriMatcher.addURI("com.unisys.movieDatabase.provider","movies",1);

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Log.i("@uniSys", "Content Provider bulk Insert");
              for(int i=0;i< values.length;i++)
              {
                  movieDatabase.insertToMoviesTable(values[i]);
              }
        getContext().getApplicationContext().getContentResolver().notifyChange(uri,null);
        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i("@uniSYs","Content Provider delete");
        int noOfrowsEffected=movieDatabase.deleteMovie(selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);

                 return  noOfrowsEffected;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
             Log.i("@uniSYs","Content Provider Insert");

             movieDatabase.insertToMoviesTable(values);
              getContext().getContentResolver().notifyChange(uri,null);

        return  null;
    }

    @Override
    public boolean onCreate() {
        Log.i("@uniSys","Content Provider onCreate");
         movieDatabase=new MovieDatabase(getContext());
          movieDatabase.openWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=null;
                    switch (uriMatcher.match(uri))
                    {

                        case 1:
                        cursor=movieDatabase.getMoviesList(projection,selection,selectionArgs,sortOrder);
                            break;

                    }

                    cursor.setNotificationUri(getContext().getApplicationContext().getContentResolver(),uri);


                    return cursor;

            }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        Log.i("@uniSys","ContenProvider Update");
        int noOfRecordsUpdated=movieDatabase.updateMoviesTable(values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return noOfRecordsUpdated;
    }

}
