package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 3/11/17.
 */

class MovieDatabase {
    Context mContext;
    MovieDatabaseSQLiteOpenHelper movieDatabaseSQLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;
    MovieDatabase(Context context)
    {
        mContext=context;
        movieDatabaseSQLiteOpenHelper= new MovieDatabaseSQLiteOpenHelper(context,"Moviedb24",null,1);



    }

    class MovieDatabaseSQLiteOpenHelper extends SQLiteOpenHelper
    {
        public MovieDatabaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE MOVIES_TABLE (_id INTEGER PRIMARY KEY, " +
                    "MOVIE_NAME TEXT, " +
                    "MOVIE_LANGUAGE TEXT," +
                    "MOVIE_AVAIL_TKTS TEXT )");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

     public void openReadableDatabase()
     {
         sqLiteDatabase=movieDatabaseSQLiteOpenHelper.getReadableDatabase();

     }

    }

    public void openWritableDatabase()
    {
        sqLiteDatabase=movieDatabaseSQLiteOpenHelper.getWritableDatabase();
    }

    public Cursor getMoviesList(String[] projection,String selection,String[] selectionArgs,String orderBy)
    {
                 Cursor cursor=sqLiteDatabase.query("MOVIES_TABLE",projection,selection,selectionArgs,null,null,orderBy);
                cursor.moveToFirst();
        return cursor;
    }

    public int deleteMovie(String selection, String[] selectionArgs)
    {
        int noOfrowEffected= sqLiteDatabase.delete("MOVIES_TABLE",selection,selectionArgs);

        return noOfrowEffected;
    }

    public void insertToMoviesTable(ContentValues contentValues)
    {
        sqLiteDatabase.insert("MOVIES_TABLE",null,contentValues);



    }
    public int updateMoviesTable(ContentValues contentValues,String selection, String[] selectionArgs)

    {

       int noOfRecordsUpdated= sqLiteDatabase.update("MOVIES_TABLE",contentValues,selection,selectionArgs);

            return noOfRecordsUpdated;
    }



}
