package za.ac.iie.opsc.weatherapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {
    /*Class that handles the network connections to AccuWeather*/

    private static final String WEATHERBASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    private static final String PARAM_METRIC = "metric";
    private static final String METRIC_VALUE = "true";
    private static final String PARAM_API_KEY = "apikey";
    private static final String LOGGING_TAG = "URLWECREATED";

    private NetworkUtil(){


    }

    public static URL buildURLForWeather(){
        Uri buildUri = Uri.parse(WEATHERBASE_URL).buildUpon().appendQueryParameter(PARAM_API_KEY,BuildConfig.ACCUWEATHER_API_KEY).appendQueryParameter(PARAM_METRIC, METRIC_VALUE).build();
        URL url = null;

        try{
            url = new URL(buildUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        Log.i(LOGGING_TAG, "buildURLForWeather: " + url);
        return url;
    }

    //Creating a connection to a URL and reading whatever data is available from the service
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");
            boolean hasInput = scanner.hasNext();

            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }

        }finally{
            urlConnection.disconnect();
        }
    }
}
