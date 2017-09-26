package com.domgarr.android.booklisting;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
public static final String LOG_TAG = "MainActivity";

    public static final String GOOGLE_BOOK_QUERY = "https://www.googleapis.com/books/v1/volumes?q=";

    private ListView mListView;
    private SearchView mSearchView;

    private BookAdapter mBookAdapter;
    private List<Book> books;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = (SearchView) findViewById(R.id.search_view);




        books = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.list_view);

        mBookAdapter = new BookAdapter(getApplicationContext(), 0, (ArrayList) books);
        mListView.setAdapter(mBookAdapter);




        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == null || query.isEmpty() )
                    return false;

                DownloadBooksTask downloadBooksTask = new DownloadBooksTask();
                downloadBooksTask.execute(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    private class DownloadBooksTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... params) {
            return BookQueryUtils.getBooks(GOOGLE_BOOK_QUERY + params[0]);
        }

        protected void onPostExecute(List<Book> result) {
            books = result;
            mBookAdapter.clear();
            mBookAdapter.addAll(books);
            mBookAdapter.notifyDataSetChanged();
        }
    }
}
