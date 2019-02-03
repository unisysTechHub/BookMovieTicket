package com.uisys.bookmovieticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListMoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    CursorAdapter cursorAdapter;

    private OnFragmentInteractionListener mListener;

    public ListMoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMoviesFragment newInstance(String param1, String param2) {
        ListMoviesFragment fragment = new ListMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_list_movies, container, false);
        listView= (ListView) view.findViewById(R.id.listView);
         cursorAdapter=new MoviesListCursorAdapter(getActivity(),null,false,mParam1);
        listView.setAdapter(cursorAdapter);
        if(mParam1.equals("English"))
        {
            getActivity().getSupportLoaderManager().initLoader(1, null, new CursorLoaderForListMovies());
        }

        if(mParam1.equals("Hindi"))
        {
            getActivity().getSupportLoaderManager().initLoader(2, null, new CursorLoaderForListMovies());

        }

        if(mParam1.equals("Telugu"))
        {
            getActivity().getSupportLoaderManager().initLoader(3, null, new CursorLoaderForListMovies());

        }




        return view;
    }


    class CursorLoaderForListMovies implements LoaderManager.LoaderCallbacks<Cursor>
    {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection ;
            String   selection;
            String[] selectionArgs;
            String sortOrder;
            CursorLoader cursorLoader=null;
              if(id == 1) {

                  projection = new String[]{"_id", "MOVIE_NAME", "MOVIE_LANGUAGE", "MOVIE_AVAIL_TKTS"};
                  selection = "MOVIE_LANGUAGE=?";
                  System.out.println("@uniSys Language :" + mParam1);
                  selectionArgs = new String[]{mParam1};
                  sortOrder = "MOVIE_NAME ASC";

                  cursorLoader = new CursorLoader(getContext().getApplicationContext(), URLList.MOVIELIST_URI, projection, selection, selectionArgs, sortOrder);
              }

            if(id == 2) {

                projection = new String[]{"_id", "MOVIE_NAME", "MOVIE_LANGUAGE", "MOVIE_AVAIL_TKTS"};
                selection = "MOVIE_LANGUAGE=?";
                System.out.println("@uniSys Language :" + mParam1);
                selectionArgs = new String[]{mParam1};
                sortOrder = "MOVIE_NAME ASC";

                cursorLoader = new CursorLoader(getContext().getApplicationContext(), URLList.MOVIELIST_URI, projection, selection, selectionArgs, sortOrder);
            }

            if(id == 3) {

                projection = new String[]{"_id", "MOVIE_NAME", "MOVIE_LANGUAGE", "MOVIE_AVAIL_TKTS"};
                selection = "MOVIE_LANGUAGE=?";
                System.out.println("@uniSys Language :" + mParam1);
                selectionArgs = new String[]{mParam1};
                sortOrder = "MOVIE_NAME ASC";

                cursorLoader = new CursorLoader(getContext().getApplicationContext(), URLList.MOVIELIST_URI, projection, selection, selectionArgs, sortOrder);
            }




            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.i("@uniSys","onLoadFinished");
            System.out.println("@uniSys cursor Count :" + data.getCount());

                cursorAdapter.swapCursor(data);
                cursorAdapter.notifyDataSetChanged();



        }


        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            if(loader.getId() == 1 )
            {
                cursorAdapter.swapCursor(null);
                cursorAdapter.notifyDataSetChanged();

            }

        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
