package com.domgarr.android.booklisting;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    public static final String LOG_TAG = "MainActivity";

    public static final int LOADER_ID = 1;

    private ListView mListView;
    private SearchView mSearchView;

    private BookAdapter mBookAdapter;
    private List<Book> books;

    private String mQuery;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        books = new ArrayList<>();

        initViews();
        initAdapter();

        getLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query == null || query.isEmpty() )
                    return false;

                mQuery = query;
                //Loaders cache query results!
                getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, mQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> result) {
        books = result;
        mBookAdapter.clear();

        if(books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
        //mBookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
        books.clear();
    }

    private void initViews(){
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
    }

    private void initAdapter(){
        mBookAdapter = new BookAdapter(getApplicationContext(), 0, (ArrayList) books);
        mListView.setAdapter(mBookAdapter);
    }
}
