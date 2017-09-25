package com.domgarr.android.booklisting;

/**
 * Created by Domenic on 2017-09-24.
 */

public class Book {
    private final String mTitle;
    private final String mAuthor;
    private final String mPublishedDate;
    private final String mDescription;
    private final String mImageLink;
    private final Double mAverageRating;
    private final String mPreviewLink;

    //This class implements a builder pattern. It is useful when the constructor
    //requires many parameters. Extremely useful when there are optional parameters.
    //Although my implementation of Book will require everything, the one pros of using
    //Builder pattern is that it makes code more readable.
    public static class BookBuilder {
        //Required Parameters
        private String mTitle;
        private String mAuthor;
        private String mImageLink;
        private String mDescription;

        public BookBuilder(String title, String author, String description, String imageLink) {
            mTitle = title;
            mAuthor = author;
            mImageLink = imageLink;
            mDescription = description;
        }

        //Optional Parameters - Initialized to default values
        private  String mPublishedDate = null;
        private  Double mAverageRating = null;
        private  String mPreviewLink = null;

        public BookBuilder publishedDate(String publishedDate){
            mPublishedDate = publishedDate;
            return this;
        }

        public BookBuilder averageRating(Double averageRating){
            mAverageRating = averageRating;
            return this;
        }

        public BookBuilder previewLink(String previewLink){
            mPreviewLink = previewLink;
            return this;
        }

        public Book build(){
            return new Book(this);
        }
    }

    private Book (BookBuilder bookBuilder){
        mTitle = bookBuilder.mTitle;
        mAuthor = bookBuilder.mAuthor;
        mPublishedDate = bookBuilder.mPublishedDate;
        mDescription = bookBuilder.mDescription;
        mImageLink = bookBuilder.mImageLink;
        mAverageRating = bookBuilder.mAverageRating;
        mPreviewLink = bookBuilder.mPreviewLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public Double getAverageRating() {
        return mAverageRating;
    }

    public String getPreviewLink() {
        return mPreviewLink;
    }

    @Override
    public String toString() {
        return "Book{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mPublishedDate='" + mPublishedDate + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mImageLink='" + mImageLink + '\'' +
                ", mAverageRating=" + mAverageRating +
                ", mPreviewLink='" + mPreviewLink + '\'' +
                '}';
    }
}
