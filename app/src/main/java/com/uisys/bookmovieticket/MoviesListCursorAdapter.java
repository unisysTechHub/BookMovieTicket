package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 3/12/17.
 */

public class MoviesListCursorAdapter extends CursorAdapter {
    Context mContext;
    String movieLang;
    private Uri moviesList_URI = Uri.parse("content://com.unisys.movieDatabase.provider/movies");

    public MoviesListCursorAdapter(Context context, Cursor c, boolean autoRequery,String movieLang) {
        super(context, c, autoRequery);
        this.movieLang=movieLang;
        mContext=context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.movieslistrow,null);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(viewHolder == null)
        {

            viewHolder= new ViewHolder(view);
            view.setTag(viewHolder);


        }

        viewHolder.movieNameT.setText(cursor.getString(cursor.getColumnIndex("MOVIE_NAME")));
        viewHolder.movieTktsAvailT.setText(cursor.getString(cursor.getColumnIndex("MOVIE_AVAIL_TKTS")));
        viewHolder.bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                           LinearLayout rowLayout = (LinearLayout) v.getParent();
                           TextView textView = (TextView) rowLayout.findViewById(R.id.movieName);
                           final String movieName = (String) textView.getText();
                           System.out.println("@uniSys book ticket :" + movieName ) ;


                StringRequest stringRequest= new StringRequest(Request.Method.POST, URLList.fbBookTicketURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Firbase Database
                          Log.i("@uniSys", "response" + response);
                        /*

                        try {
                            String movieAvailTkts =new JSONObject(response).getString(movieName);
                            System.out.println("@uniSys" + "bookticket onResponse available tickets " + movieAvailTkts);
                            ContentValues contentValues= new ContentValues();
                            contentValues.put("MOVIE_AVAIL_TKTS",movieAvailTkts);
                            String selection = "MOVIE_NAME=?";
                            String[] selectionArgs = new String[]{movieName};
                            context.getContentResolver().update(moviesList_URI,contentValues,selection,selectionArgs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        */

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("@uniSys bookTicket volley Error " +  error );

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("movieName",movieName);
                        map.put("movieLang",movieLang);
                        return map;
                    }
                };

                VolleySingleton.getInstance(mContext).getRequestQueue().add(stringRequest);


            }
        });
        cursor.moveToNext();

    }

    class ViewHolder
    { TextView movieNameT;
      TextView movieTktsAvailT;
      Button bookTicket;

        ViewHolder(View view)
        {
            movieNameT = (TextView) view.findViewById(R.id.movieName);
            movieTktsAvailT= (TextView) view.findViewById(R.id.movieTktsavail);
            bookTicket= (Button) view.findViewById(R.id.bookTicket);


        }


    }
}
