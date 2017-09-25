package com.domgarr.android.booklisting;

import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
public static final String LOG_TAG = "MainActivity";

    public static final String GOOGLE_BOOK_QUERY = "https://www.googleapis.com/books/v1/volumes?q=Java";

    private ListView mListView;
    private BookAdapter mBookAdapter;
    private List<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.list_view);

        mBookAdapter = new BookAdapter(getApplicationContext(), 0, (ArrayList) books);
        mListView.setAdapter(mBookAdapter);

        DownloadBooksTask downloadBooksTask = new DownloadBooksTask();
        downloadBooksTask.execute();

    }


    private class DownloadBooksTask extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... params) {
            return BookQueryUtils.getBooks(GOOGLE_BOOK_QUERY);
        }

        protected void onPostExecute(List<Book> result) {
            books = result;
            mBookAdapter.addAll(books);
            mBookAdapter.notifyDataSetChanged();
        }
    }
}
