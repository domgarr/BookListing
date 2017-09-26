package com.domgarr.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Domenic on 2017-09-26.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mQuery;


    public BookLoader(Context context, String query){
        super(context);
        mQuery = query;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(mQuery == null){
            return null;
        }

        List<Book> books = BookQueryUtils.getBooks(mQuery);

        return books;
    }
}
