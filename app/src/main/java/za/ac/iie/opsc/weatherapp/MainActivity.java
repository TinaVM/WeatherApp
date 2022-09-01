package za.ac.iie.opsc.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import model.DailyForecasts;
import model.Forecast;
import model.Root;

public class MainActivity extends AppCompatActivity {

    private TextView tvWeather;
    private ArrayList<Forecast> fiveDaylList = new ArrayList<Forecast>();
    private static final String LOGGING_TAG = "weatherDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWeather = findViewById(R.id.tv_weather);
        //ImageView ivAccuWeather = findViewById(R.id.iv_accuweather);
        /*ivAccuWeather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.accuweather.com/"));
                startActivity(intent);

            }
        });*/



    /*JSONObject rootWeatherData = new JSONObject(weatherJSON);
    rootWeatherData.accumulate();*/


        URL url = NetworkUtil.buildURLForWeather();
        //TODO: MAKE ASYNCHRONOUS CALL
        new FetchWeatherData().execute(url);



    }

    class FetchWeatherData extends AsyncTask<URL,Void,String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherURL = urls[0];
            String weatherData = null;
            try {
                weatherData = NetworkUtil.getResponseFromHttpUrl(weatherURL);
                //tvWeather.setText(answer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherData;
        }

        @Override
        protected void onPostExecute(String weatherData) {
            if (weatherData != null) {
                //tvWeather.setText(weatherData);
                consumeJson(weatherData);
            }
            super.onPostExecute(weatherData);
        }
    }

        protected void consumeJson(String weatherJSON){
            if(fiveDaylList != null){
                fiveDaylList.clear();
            }

            if(weatherJSON != null) {
                try {
                    //get the root JSON object
                    JSONObject rootweatherData = new JSONObject(weatherJSON);

                    //find daily forecasts array
                    JSONArray fiveDayForecast = rootweatherData.getJSONArray("DailyForecasts");

                    //get data from each entry in the array
                    for (int i = 0; i < fiveDayForecast.length(); i++) {
                        Forecast forecastObject = new Forecast();

                        JSONObject dailyWeather = fiveDayForecast.getJSONObject(i);

                        //get date
                        String date = dailyWeather.getString("Date");
                        Log.i(LOGGING_TAG, "consumeJson: Date" + date);
                        forecastObject.setDate(date);

                        //get minimum temperature
                        JSONObject temperatureObject = dailyWeather.getJSONObject("Temperature");
                        JSONObject minTempObject = temperatureObject.getJSONObject("Minimum");
                        String minTemp = minTempObject.getString("Value");
                        Log.i(LOGGING_TAG,"consumeJson: minTemp" + minTemp);
                        forecastObject.setMinimumTemperature(minTemp);

                        //get maximum temperature
                        JSONObject maxTempObject = temperatureObject.getJSONObject("maximum");
                        String maxTemp = maxTempObject.getString("Value");
                        Log.i(LOGGING_TAG,"consumeJson: maxTemp" + maxTemp);
                        forecastObject.setMaximumTemperature(maxTemp);

                        fiveDaylList.add(forecastObject);

                        tvWeather.append("Date: " + date + " Min: " + minTemp + " Max: " + maxTemp + "\n");
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }


                    if(weatherJSON != null){
                        Gson gson = new Gson();
                        Root weatherData = gson.fromJson(weatherJSON, Root.class);
                        for(DailyForecasts forecast : weatherData.getDailyForecasts()){
                            tvWeather.append("Date: " + forecast.getDate().substring(0,10) + " Min: " + forecast.getTemperature().getMinimum().getValue() + " Max: "
                            + forecast.getTemperature().getMaximum().getValue() + "\n");
                        }
                    }

        }


    }
}