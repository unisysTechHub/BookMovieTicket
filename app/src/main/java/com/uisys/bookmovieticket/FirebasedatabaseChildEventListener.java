package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 3/19/17.
 */

public class FirebasedatabaseChildEventListener implements ChildEventListener {

    Context context;
    String movieLang=null;
    int   calledForNoOfChild=0;

    FirebasedatabaseChildEventListener(Context context,String movieLang)
    {

        this.context=context;
        this.movieLang=movieLang;



    }


    @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Firebase", MODE_PRIVATE);
        boolean addChild = sharedPreferences.getBoolean("addChild"+movieLang, false);
        calledForNoOfChild = calledForNoOfChild + 1;
        Log.i("@uniSys", "addchild :" + addChild);

        if (addChild) {

            ContentValues contentValues = new ContentValues();
            Log.i("@uniSys", "on Childe data Aadded" + "key :" + dataSnapshot.getKey() + "value :" + dataSnapshot.getValue() + " " + s);

            String newMovieName = dataSnapshot.getKey();
            String newMovieAvailTkts = dataSnapshot.getValue() + "";

//            Log.i("@uniSys","on Childe data Aadded" + "key :"+dataSnapshot.getKey()+"value :" +dataSnapshot.getValue() + " " + s);
            contentValues.put("MOVIE_NAME", newMovieName);
            contentValues.put("MOVIE_LANGUAGE", movieLang);
            contentValues.put("MOVIE_AVAIL_TKTS", newMovieAvailTkts);
            context.getContentResolver().insert(URLList.MOVIELIST_URI, contentValues);

            long childCount = sharedPreferences.getLong(movieLang, 1000);
            childCount=childCount+1;
            Log.i("@uniSys"," Child Added count :"+childCount);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(movieLang,childCount);
            editor.commit();

        }
        if(!addChild)
        {

            long childCount = sharedPreferences.getLong(movieLang, 1000);

            if (childCount == calledForNoOfChild) {

                Log.i("@uniSys Flag Set","for Lang:"+movieLang+"child count" +childCount);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("addChild"+movieLang, true);
                editor.commit();

            }
        }

    }


        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.i("@uniSys","on Childe data Changed " + dataSnapshot.getValue() + " " + s);
            ContentValues contentValues = new ContentValues();
            String movieName =dataSnapshot.getKey();
            String movieAvailTkts =  dataSnapshot.getValue()+"";
            contentValues.put("MOVIE_AVAIL_TKTS",movieAvailTkts);
            String selection = "MOVIE_NAME=?";
            String[] selectionArgs = new String[]{movieName};
            context.getContentResolver().update(URLList.MOVIELIST_URI,contentValues,selection,selectionArgs);

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.i("@uniSys","onChidRemove"+dataSnapshot.getKey());
            String newMovieName =dataSnapshot.getKey();
            String selection = "MOVIE_NAME=?";
            String[] selectionArgs = new String[]{newMovieName};

            context.getContentResolver().delete(URLList.MOVIELIST_URI,selection,selectionArgs);


            SharedPreferences sharedPreferences = context.getSharedPreferences("Firebase", MODE_PRIVATE);
            long childCount = sharedPreferences.getLong(movieLang, 1000);
            childCount=childCount-1;
            Log.i("@uniSys"," Child Added count :"+childCount);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(movieLang,childCount);
            editor.commit();


        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.i("@uniSys","on Childe data Moved" + dataSnapshot.getValue() + " " + s);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }





}
