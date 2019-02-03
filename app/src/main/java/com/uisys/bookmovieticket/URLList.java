package com.uisys.bookmovieticket;

import android.net.Uri;

/**
 * Created by Admin on 3/12/17.
 */

 class URLList {


    static public Uri MOVIELIST_URI = Uri.parse("content://com.unisys.movieDatabase.provider/movies");
    static public String registerTokenURL = "http://unisys.xyz/registerToken.php";
    static public String getAllMoviesURL = "http://unisys.xyz/getAllmovies.php";
    static public String bookTicketURL = "http://unisys.xyz/bookticket.php";
    static public String fbBookTicketURL ="https://us-central1-bookmoiveticket.cloudfunctions.net/bookMovieTkt";
}
