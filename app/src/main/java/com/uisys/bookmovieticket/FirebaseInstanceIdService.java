package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Admin on 3/12/17.
 */

public  class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    String tokenId;
    @Override
    public void onTokenRefresh() {

         tokenId =FirebaseInstanceId.getInstance().getToken();
        System.out.println("@uniSys token id :" + tokenId);
        getAllMoviesDatafromFirebaseCloud();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLList.registerTokenURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("@uniSys response :" + new JSONObject(response).getString("message"));



                    getAllMoviesDatafromFirebaseCloud();
                    //getAllMoviesDataFromServer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("@uniSys registerToken Volley Error " + error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("tokenId",tokenId);
                return map;
            }
        };

//        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);

    }


    public void getAllMoviesDataFromServer()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLList.getAllMoviesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.i("@uniSys","getAllmoives onResponse");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String movieName;
                        String movieLang;
                        String movieTktsAvail;


                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("list");

                            ContentValues[] contentValues= new ContentValues[jsonArray.length()];
                            for(int i=0;i< jsonArray.length();i++)
                            {
                                movieName= jsonArray.getJSONObject(i).getString("movieName");
                                movieLang=jsonArray.getJSONObject(i).getString("movieLang");
                                movieTktsAvail=jsonArray.getJSONObject(i).getString("TktsAvailable");
                                System.out.println("@uniSys moives dta :" + movieName + movieLang + movieTktsAvail);
                                contentValues[i]=new ContentValues();
                                contentValues[i].put("MOVIE_NAME",movieName);
                                contentValues[i].put("MOVIE_LANGUAGE",movieLang);
                                contentValues[i].put("MOVIE_AVAIL_TKTS",movieTktsAvail);

                            }
                            getApplicationContext().getContentResolver().bulkInsert(URLList.MOVIELIST_URI,contentValues);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("@uniSys getAllMovies Volley error" + error);

            }
        });

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);



    }

    public void  getAllMoviesDatafromFirebaseCloud()
    {
        Log.d("@unisys", "valueEventListener");

        ValueEventListener valueEventListener = new ValueEventListener() {
            String movieName;
            String movieLang;
            String movieTktsAvail;
            long allChildCount=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot child: dataSnapshot.getChildren())
                {
                    movieLang=child.getKey();
                    System.out.println("@uniSys value event listener" + child.getKey());
                    allChildCount=  allChildCount+child.getChildrenCount();
                    ContentValues[] contentValues= new ContentValues[(int) child.getChildrenCount()];

                    Iterator iterator= child.getChildren().iterator();
                    int i=0;
                    while (iterator.hasNext())
                    {

                        DataSnapshot movie= (DataSnapshot) iterator.next();


                        System.out.println("@uniSys all " + movie.getKey()+movie.getValue());
                        movieName=movie.getKey();
                        movieTktsAvail=  movie.getValue()+"";
                        contentValues[i]=new ContentValues();
                        contentValues[i].put("MOVIE_NAME",movieName);
                        contentValues[i].put("MOVIE_LANGUAGE",movieLang);
                        contentValues[i].put("MOVIE_AVAIL_TKTS",movieTktsAvail);
                        i=i+1;
                    }

                    getApplicationContext().getContentResolver().bulkInsert(URLList.MOVIELIST_URI,contentValues);


                    SharedPreferences sharedPreferences = getSharedPreferences("Firebase",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("addChild"+movieLang.trim(), true);
                    editor.putLong(movieLang.trim(),allChildCount);
                    editor.commit();

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        FirebaseDatabase.getInstance().getReference().
                addListenerForSingleValueEvent(valueEventListener);

    }
}
