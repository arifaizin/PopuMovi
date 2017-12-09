package id.co.imattudio.popumovi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ArrayList<MovieModel> listData = new ArrayList<>();
    Integer posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //terima data
        listData = getIntent().getParcelableArrayListExtra(" ");
        posisi = getIntent().getIntExtra("DATA_POSISI", 0);

//        Toast.makeText(this, ""+listData.get(posisi).getTitle(), Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle(listData.get(posisi).getTitle());

        TextView tvSinopsis = (TextView) findViewById(R.id.tv_overview);
        tvSinopsis.setText(listData.get(posisi).getOverview());

        ImageView iv_backdrop = (ImageView) findViewById(R.id.iv_detail_gambar);
        Glide.with(DetailActivity.this).load("https://image.tmdb.org/t/p/w500"+listData.get(posisi).getPosterPath()).into(iv_backdrop);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
