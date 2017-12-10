package id.co.imattudio.popumovi.connection;

import id.co.imattudio.popumovi.ListMovieModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by idn on 12/9/2017.
 */

public interface ApiServices {
    @GET("movie/popular?api_key=b08e3495841838f530552c2b261e00b1&language=en-US&page=1")
    Call<ListMovieModel> ambilFilmPopuler();

    //top rated disini
    @GET("movie/top_rated?api_key=b08e3495841838f530552c2b261e00b1&language=en-US&page=1")
    Call<ListMovieModel> ambilFilmTopRated();
}
