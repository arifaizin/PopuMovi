package id.co.imattudio.popumovi;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.co.imattudio.popumovi.database.MovieContract;

public class DetailActivity extends AppCompatActivity {

    ArrayList<MovieModel> listData = new ArrayList<>();
    Integer posisi;
    SharedPreferences pref;
    Boolean isFavorit = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting preference
        pref = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);

        //terima data
        listData = getIntent().getParcelableArrayListExtra("DATA_FILM");
        posisi = getIntent().getIntExtra("DATA_POSISI", 0);

        Toast.makeText(this, ""+listData.get(posisi).getId(), Toast.LENGTH_SHORT).show();

        //set data
        getSupportActionBar().setTitle(listData.get(posisi).getTitle());

        TextView tvSinopsis = (TextView) findViewById(R.id.tv_overview);
        tvSinopsis.setText(listData.get(posisi).getOverview());

        ImageView iv_backdrop = (ImageView) findViewById(R.id.iv_detail_gambar);
        Glide.with(DetailActivity.this).load("https://image.tmdb.org/t/p/w500"+listData.get(posisi).getPosterPath()).into(iv_backdrop);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorit){
                    //kita rubah pref dari true jadi false
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+listData.get(posisi).getTitle(), false);
                    editor.commit();
                    isFavorit = false;
                    Snackbar.make(view, "Favorit dihapus", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                    hapusDatabase();

                } else {
                    //kita rubah pref dari false jadi true
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("FAVORIT"+listData.get(posisi).getTitle(), true);
                    editor.commit();
                    isFavorit = true;
                    Snackbar.make(view, "Favorit ditambah", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    simpanDatabase();
                }
                updateFAB();
            }
        });

        //ambil data dari preference
        isFavorit = pref.getBoolean("FAVORIT"+listData.get(posisi).getTitle(), false);

        //harus di bawah fab
        updateFAB();
        //kalau favorit nanti love full
        //jalau ndak nanti love kosong
    }

    private void hapusDatabase() {
        int numRowsDeleted = getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI
                        .buildUpon()
                        .appendPath(String.valueOf(listData.get(posisi).getId()))
                        .build(), null, null);

        if (numRowsDeleted != 0) {
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    private void simpanDatabase() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_ID, listData.get(posisi).getId());
        cv.put(MovieContract.MovieEntry.COLUMN_JUDUL, listData.get(posisi).getTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER, listData.get(posisi).getPosterPath());
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, listData.get(posisi).getOverview());
        //3 tambah sini

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);
        if (ContentUris.parseId(uri) > 0){
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFAB() {
        if (isFavorit){
            //jika true
            fab.setImageResource(R.drawable.ic_action_favorite);
        } else {
            //jika false
            fab.setImageResource(R.drawable.ic_action_notfavorit);
        }
    }
}
