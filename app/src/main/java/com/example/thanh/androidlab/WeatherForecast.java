package com.example.thanh.androidlab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.icu.util.ULocale.getName;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private ProgressBar progress;
    private ImageView ivWeather;
    private TextView tvCurrent;
    private TextView tvMin;
    private TextView tvMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progress = (ProgressBar)findViewById(R.id.pbWeather);
        progress.setVisibility(View.VISIBLE);

        ivWeather = (ImageView)findViewById(R.id.ivWeather);
        tvCurrent = (TextView) findViewById(R.id.tvCurrent);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tvMax = (TextView) findViewById(R.id.tvMax);

        new ForecastQuery().execute();
    }
    class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemp;
        private String maxTemp;
        private String currrentTemp;
        private String currentWeather;
        private Bitmap bitmap;

        @Override
        protected String doInBackground(String... args){
            InputStream stream;
            //checking network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                Log.i(ACTIVITY_NAME, "Device is connected to network");
            }
            else{ Log.e(ACTIVITY_NAME, "No network connection is available"); }

            // connecting to url and reading data input stream
            try {
                URL url =new URL ("http://api.openweathermap.org/data/2.5/weather?q=ottawa," +
                        "ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
                //URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa," +
                //        "ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); //in milliseconds
                conn.setConnectTimeout(15000); //in millisenconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                //test
                Log.d(ACTIVITY_NAME, "connecting with url..");
                conn.connect();
                //test
                Log.d(ACTIVITY_NAME, "reading stream");
                stream = conn.getInputStream();
                //test
                Log.d(ACTIVITY_NAME, "stream is: " + stream);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(stream, null);

                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType != XmlPullParser.START_TAG){
                        eventType = parser.next();
                        continue;
                    }
                    else{
                        if(parser.getName().equals("temperature")){
                            currrentTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }else if(parser.getName().equals("weather")){
                            currentWeather = parser.getAttributeValue(null, "icon");
                        }
                        eventType = parser.next();
                    }
                }
                conn.disconnect();

                //connecting or searching through file to get weather image
                if(fileExist(currentWeather + ".png")){
                    Log.i(ACTIVITY_NAME, "Weather image exists, read from file");
                    File file = getBaseContext().getFileStreamPath(currentWeather + ".png");
                    FileInputStream fis = new FileInputStream(file);
                    bitmap = BitmapFactory.decodeStream(fis);
                }else {
                    Log.i(ACTIVITY_NAME, "Weather image does not exist, download from URL");
                    URL imageUrl = new URL("http://openweathermap.org/img/w/" + currentWeather + ".png");
                    conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    stream = conn.getInputStream();

                    bitmap = BitmapFactory.decodeStream(stream);

                    FileOutputStream fos = openFileOutput(currentWeather + ".png", Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    conn.disconnect();
                }
                publishProgress(100);
            }
            catch(FileNotFoundException fne){
                Log.e(ACTIVITY_NAME, fne.getMessage());
            }
            catch (XmlPullParserException parserException){
                Log.e(ACTIVITY_NAME, parserException.getMessage());
            }
            catch(IOException e){
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String args){
            progress.setVisibility(View.INVISIBLE);
            String degree = Character.toString((char) 0x00B0);
            tvCurrent.setText("Current: " + currrentTemp + degree+"C");
            tvMin.setText("Min: " + minTemp + degree+"C");
            tvMax.setText("Max: " + maxTemp + degree+"C");
            ivWeather.setImageBitmap(bitmap);
        }

        public boolean fileExist(String name){
            File file = getBaseContext().getFileStreamPath(name);
            return file.exists();
        }
    }
}


