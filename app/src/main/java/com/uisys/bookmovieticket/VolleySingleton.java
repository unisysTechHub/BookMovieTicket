package com.uisys.bookmovieticket;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by Sujatha on 01-03-2017.
 */
public class VolleySingleton {
    private static RequestQueue requestQueue=null;
    private static ImageLoader imageLoader;
    private static VolleySingleton volleySingleTon;
    private Context mContext;

    private VolleySingleton(Context context) {
        mContext=context;
        requestQueue =getRequestQueue();
        imageLoader= new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });

    }

    static VolleySingleton getInstance(Context context)
    {
        if(volleySingleTon == null) {
            volleySingleTon = new VolleySingleton(context);
        }

      return  volleySingleTon;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {


            requestQueue = Volley.newRequestQueue(mContext);


        }

        return requestQueue;
    }

    public ImageLoader getImageLoader()
    {

        return imageLoader;
    }

}
