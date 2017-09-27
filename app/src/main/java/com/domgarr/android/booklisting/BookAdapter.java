package com.domgarr.android.booklisting;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Domenic on 2017-09-25.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    private final String robotoMediumPath = "roboto-medium.ttf";
    private final String robotoLightPath = "roboto-light.ttf";

    private final Typeface robotoMediumTypeface;
    private final Typeface robotoLightTypeface;

    public BookAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Book> books) {
        super(context, 0, books);

        robotoMediumTypeface = Typeface.createFromAsset(context.getAssets(),  robotoMediumPath);
        robotoLightTypeface = Typeface.createFromAsset(context.getAssets(), robotoLightPath);
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
        titleTextView.setTypeface(robotoMediumTypeface);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(book.getAuthor());
        authorTextView.setTypeface(robotoLightTypeface);

        TextView publishedYearTextView = (TextView) listItemView.findViewById(R.id.published_date_text_view);
        publishedYearTextView.setText(book.getPublishedDate());
        publishedYearTextView.setTypeface(robotoLightTypeface);

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text_view);
        descriptionTextView.setText(book.getDescription());
        descriptionTextView.setTypeface(robotoLightTypeface);

        RatingBar averageRatingBar = (RatingBar) listItemView.findViewById(R.id.average_rating);

        averageRatingBar.setRating( (float) book.getAverageRating());
        Log.d("BookAdapter" , position + " Average Rating: " + averageRatingBar.getRating());
        ImageView bookImageView = (ImageView) listItemView.findViewById(R.id.book_image_view);
        Picasso.with( getContext() ).load( book.getImageLink() ).fit().into( bookImageView );

        return listItemView;
    }
}
