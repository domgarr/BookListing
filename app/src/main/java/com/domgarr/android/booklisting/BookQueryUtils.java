package com.domgarr.android.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Domenic on 2017-09-24.
 */

public final class BookQueryUtils {
    private static final String LOG_TAG = "BookQueryUtils";
    //This object is not meant to hold state.
    private BookQueryUtils(){}

    public static ArrayList<Book> getBooks(String queryUrlString){
        ArrayList<Book> books = new ArrayList<>();



        URL url = createUrl(queryUrlString);
        if(url == null){
            return null;
        }

        try {
            JSONObject booksRoot = new JSONObject(makeHttpRequest(url));

            JSONArray itemsArray = booksRoot.optJSONArray("items");

            String title = null;
            String author = null;
            String publishedDate = null;
            String description = null;
            double averageRating = 0.0;
            String imageLink = null;
            String previewLink = null;

            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject itemObject = (JSONObject) itemsArray.get(i);
                JSONObject volumeInfo =  itemObject.getJSONObject("volumeInfo");


                title = volumeInfo.getString("title");

                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                if(authorsArray != null){
                    author = authorsArray.toString();
                }

                publishedDate = volumeInfo.getString("publishedDate");
                description = volumeInfo.optString("description");
                averageRating =  volumeInfo.optDouble("averageRating", 0) ;
                imageLink =  volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                previewLink = volumeInfo.getString("previewLink");

                books.add(new Book.BookBuilder(title,author,description,imageLink)
                        .publishedDate(publishedDate)
                        .averageRating(averageRating)
                        .previewLink(previewLink)
                        .build());
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON statement " , e );
        }

        return books;
    }

    private static URL createUrl(String urlString){


        URL url = null;
        if(urlString == null || urlString.isEmpty()){
            return null;
        }

        try{
            //https://stackoverflow.com/questions/28482801/how-to-request-json-that-have-spaces-in-url
            urlString = urlString.replaceAll(" ", "%20");
            url = new URL(urlString);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }


        return url;
    }

    private static String makeHttpRequest(URL queryUrl){
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            //Begin pattern.
            //Obtain new connection and cast to HttpURLConnection;
            urlConnection = (HttpURLConnection) queryUrl.openConnection();
            //Prepare request.
            // https://stackoverflow.com/questions/3069382/what-is-the-difference-between-connection-and-read-timeout-for-sockets
            //Throws an exception if timeout expires. This timeout is for reading data.
            urlConnection.setReadTimeout(10000);
            //Timeout for making initial connection
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //Reads data one byte at a time.
                inputStream = urlConnection.getInputStream();

                jsonResponse = readFromStream(inputStream);
            }else{
                Log.d(LOG_TAG, "Unexpected response code value: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //Clean up
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();

        if(inputStream == null){
            return null;
        }
        //Decodes byte stream into character stream.
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        //Efficeient reading of characters by buffering characters, that is
        //instead of reading character one by one, we are reading in blocks.
        BufferedReader reader = new BufferedReader(inputStreamReader);

        try {
            String line;
            while((line = reader.readLine()) != null ){
                output.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
