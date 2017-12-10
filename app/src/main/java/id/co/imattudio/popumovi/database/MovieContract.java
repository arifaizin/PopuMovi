package id.co.imattudio.popumovi.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by idn on 12/10/2017.
 */

public class MovieContract {
    public static final String AUTHORITY = "id.co.imastudio.popumovi";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "listfilm";

    public static final class MovieEntry implements BaseColumns {

        //Untuk Uri
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        //untuk sqlite
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_JUDUL = "title";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        //1 tambah sini

    }
}