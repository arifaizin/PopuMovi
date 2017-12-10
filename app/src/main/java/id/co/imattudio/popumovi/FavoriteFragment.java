package id.co.imattudio.popumovi;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.co.imattudio.popumovi.database.MovieContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    RecyclerView recycler;
    ArrayList<MovieModel> listdata = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycler = fragmentView.findViewById(R.id.recyclerView);
        // 1 Dataset
        // data dummy
        //fori
//        for (int i = 0; i < 20; i++) {
//            MovieModel movie1 = new MovieModel("Film Apik", "https://flagig.com/wp-content/uploads/2015/11/starwars.jpg");
//            listdata.add(movie1);
//        }

        //data real
        getActivity().getSupportLoaderManager().initLoader(100, null, this);

        // 2 Adapter
        MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
        recycler.setAdapter(adapter);

        // 3 Layout Manager
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return fragmentView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case 100:
                return new CursorLoader(
                        getActivity(),
                        MovieContract.MovieEntry.CONTENT_URI,
                        null, null, null, null);
            default:
                throw new RuntimeException("Loader Not working");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listdata.clear();
        if (data.getCount() > 0){
            if (data.moveToFirst()){
                do {
                    MovieModel movie = new MovieModel();
                    movie.setId(data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                    movie.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_JUDUL)));
                    movie.setPosterPath(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
                    movie.setOverview(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                    //4 tambah sini
                    listdata.add(movie);
                    MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
                    recycler.setAdapter(adapter);
                } while (data.moveToNext());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().initLoader(100, null, this);
    }
}
