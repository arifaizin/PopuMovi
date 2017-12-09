package id.co.imattudio.popumovi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by idn on 12/9/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private ArrayList<MovieModel> listData;
    private Context context;

    //constructor
    public MovieAdapter(ArrayList<MovieModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //sambungan dengan layout item_list
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.movie_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    //settext
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvJudulFilm.setText(listData.get(position).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+listData.get(position).getPosterPath()).into(holder.ivGambarFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DetailActivity.class);
//                pindah.putExtra("DATA_JUDUL", listData.get(position).getTitle());
                //kirim data
                pindah.putParcelableArrayListExtra("DATA_FILM", listData);
                pindah.putExtra("DATA_POSISI", position);
                context.startActivity(pindah);
            }
        });
    }

    //hitung jumlah data
    @Override
    public int getItemCount() {
        return listData.size();
    }

    // sambungin id di dalam item_list
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambarFilm;
        TextView tvJudulFilm;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGambarFilm = itemView.findViewById(R.id.iv_item_gambarfilm);
            tvJudulFilm = itemView.findViewById(R.id.tv_item_judulfilm);
        }

    }
}
