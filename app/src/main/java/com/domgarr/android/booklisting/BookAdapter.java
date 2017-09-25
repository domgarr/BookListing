package com.domgarr.android.booklisting;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domenic on 2017-09-25.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        //Will happen upon initialization since we began with empty views.
        if(listItemView == null){
            //Last param says no to attaching View to the root, instead it is a sibling
            //to the root.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent, false );
        }

        Book book = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(book.getTitle());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(book.getAuthor());

        TextView publishedYearTextView = (TextView) listItemView.findViewById(R.id.published_date_text_view);
        publishedYearTextView.setText(book.getPublishedDate());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text_view);
        descriptionTextView.setText(book.getDescription());

        RatingBar averageRatingBar = (RatingBar) listItemView.findViewById(R.id.average_rating);
        averageRatingBar.setRating( (float) book.getAverageRating());

        return listItemView;
    }
}
