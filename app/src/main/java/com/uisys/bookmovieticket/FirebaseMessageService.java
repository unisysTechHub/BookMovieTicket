package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 3/12/17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
   // private Uri moviesList_URI = Uri.parse("content://com.unisys.movieDatabase.provider/movies");


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("@uniSys remote message" );
         System.out.println("@uniSys remote message" + remoteMessage.getData());

        /*JSONObject jsonObject=new JSONObject(remoteMessage.getData());
        try {
            String newMovieName = jsonObject.getString("movieName");
            String newMovieLang = jsonObject.getString("movieLanguage");
            String newMovieAavilTkts = jsonObject.getString("tktsAvailable");
            ContentValues contentValues = new ContentValues();
            contentValues.put("MOVIE_NAME",newMovieName);
            contentValues.put("MOVIE_LANGUAGE",newMovieLang);
            contentValues.put("MOVIE_AVAIL_TKTS",newMovieAavilTkts);
            getContentResolver().insert(moviesList_URI,contentValues);


        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }


}
