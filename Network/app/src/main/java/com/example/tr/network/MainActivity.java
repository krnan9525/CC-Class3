package com.example.tr.network;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
//////changed for github
public class MainActivity extends Activity implements View.OnClickListener
{
    private static final String DEBUG_TAG = "HttpExample";
    private EditText urlText;
    //private TextView textView;
    private ListView listView;
    private Button connectButton;
    private MyDatabaseManager myDatabaseManager = new MyDatabaseManager(this);

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlText       = (EditText) findViewById(R.id.myUrl);
        //textView      = (TextView) findViewById(R.id.myText);
        listView      = (ListView) findViewById(R.id.listView);
        connectButton = (Button) findViewById(R.id.button);
        connectButton.setOnClickListener(this);
        try {
            myDatabaseManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //myDatabaseManager.CreateDb();
        /*Cursor cursor = myDatabaseManager.GetCursor();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.simple_list_item_1,cursor,new String[] {"name","checked"}, new int[]{R.id.textView,R.id.textView2});
        listView.setAdapter(simpleCursorAdapter);*/
        Cursor cursor = myDatabaseManager.GetCursor();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.simple_list_item_1,cursor,new String[] {"name","checked"}, new int[]{R.id.textView,R.id.textView2});
        listView.setAdapter(simpleCursorAdapter);
    }

    public void onClick(View view) {
        // Gets the URL from the UI's text field.

        String stringUrl 	   		= urlText.getText().toString();
        stringUrl = urlText.getText().toString();
        // PART 1: INSERT CODE HERE  - Check if there is network access available at all, if there is, go ahead and download the web page.. if there isn’t print a message to the screen
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            new DownloadWebpageTask(this).execute(stringUrl);

        }
        else
        {
            //textView.setText("No network connection!");
        }
    }

    /* Use AsyncTask as an inner class to create a task away from the main UI thread. This task takes a URL string and uses it to create an HttpUrlConnection. Once the connection has been established, the AsyncTask downloads the contents of the webpage as an InputStream. Finally, the InputStream is converted into a string, which is displayed in the UI by the AsyncTask's onPostExecute method*/
    private class DownloadWebpageTask extends AsyncTask<String, Void, String>
    {
        private Context context;
        public DownloadWebpageTask(Context context_new)
        {
            context=context_new;
        }
        protected String doInBackground(String... urls ) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(String result) {
            try {
                String s = "";
                //JSONObject reader = new JSONObject(result);
                JSONArray jsonArray = new  JSONArray(result);

                for(int i = 0 ;i < jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //s=s+"Task is:" + jsonObject.optString("title").toString() + ". And state is:" + jsonObject.optString("completed").toString() + "\n";
                    myDatabaseManager.DBInsert(jsonObject.optString("title").toString() , jsonObject.optString("completed").toString());
                }
                //textView.setText(s);
                //CursorLoader cursorLoader = new CursorLoader()
                Cursor cursor = myDatabaseManager.GetCursor();
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,R.layout.simple_list_item_1,cursor,new String[] {"name","checked"}, new int[]{R.id.textView,R.id.textView2});
                listView.setAdapter(simpleCursorAdapter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            //textView.setText(result);
        }
    }
    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 30000;

        try {
            URL url = new URL(myurl);
  /* PART 2:  INSERT CODE HERE: Use the HTTPURLConnection class to make a http connection.  Set some useful limits on the connection, such as connection timeout time, and read timeout. Set the HTTP request method to GET. Assume your connection object is called “conn”.*/
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(150000);
            conn.setConnectTimeout(100000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }


    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        InputStreamReader inputData = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputData,len);
        //char[] buffer = new char[len];
        String temp = "";
        String aux="";
        while((aux = reader.readLine()) != null)
        {
            temp+=aux;
        }

        return temp;
    }
}
