package id.co.imattudio.popumovi;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.imattudio.popumovi.connection.ApiServices;
import id.co.imattudio.popumovi.connection.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {


    public PopularFragment() {
        // Required empty public constructor
    }


    RecyclerView recycler;
    ArrayList<MovieModel> listdata = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_popular, container, false);
        recycler = fragmentView.findViewById(R.id.recyclerView);
        // 1 Dataset
        // data dummy
        //fori
//        for (int i = 0; i < 20; i++) {
//            MovieModel movie1 = new MovieModel("Film Apik", "https://flagig.com/wp-content/uploads/2015/11/starwars.jpg");
//            listdata.add(movie1);
//        }

        //data real
        ambilDataOnline();

        // 2 Adapter
        MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
        recycler.setAdapter(adapter);

        // 3 Layout Manager
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return fragmentView;
    }

    private void ambilDataOnline() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Mohon bersabar");
        progress.show();

        ApiServices api = RetrofitConfig.getApiServices();
        Call<ListMovieModel> call = api.ambilFilmTopRated();
        //pilihan jenis film disini

        call.enqueue(new Callback<ListMovieModel>() {
            @Override
            public void onResponse(Call<ListMovieModel> call, Response<ListMovieModel> response) {
                progress.dismiss();
                if(response.isSuccessful()){
                    listdata = response.body().getResults();
                    MovieAdapter adapter = new MovieAdapter(listdata, getActivity());
                    recycler.setAdapter(adapter);

                } else {
                    Toast.makeText(getActivity(), "response is not successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListMovieModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
