package com.example.bookshelf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener
, BookDetailsFragment.OnFragmentInteractionListener {
   public static String screen="";
    public static  FrameLayout frame_details;
    public static   double diagonalInches;
    public static List<Book> booksArray=new ArrayList<Book>();
    String book_id,author,title,cover_url;
    public static  MyListAdapter adapter;
  public static   SharedPreferences sharedPreferences;
    Book book=new Book();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame_details=findViewById(R.id.frame_details);

//        new SendRequest().execute();
        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("pos", 0);
        editor.commit();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
         diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, new BookListFragment());
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
//        if (screen.equals("ph")) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_details);
        if (diagonalInches>=6.5 || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            super.onBackPressed();
        }
        else {
            if (f instanceof BookDetailsFragment) {
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }   else{
                super.onBackPressed();
            }

        }

    }
//    public class SendRequest extends AsyncTask<String, Void, String> {
//
//
//        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
//
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected void onPreExecute() {
////            progressDialog.setMessage("Loading please wait......");
////            progressDialog.show();
//        }
//
//        protected String doInBackground(String... arg0) {
//            try {
//                HttpClient client = new DefaultHttpClient();
//                HttpConnectionParams.setConnectionTimeout(client.getParams(), 20000); //Timeout Limit
//                HttpResponse response;
//                try {
//
//
////                    HttpGet post = new HttpGet("https://script.google.com/macros/s/AKfycbx-7xFa0tpDYiY9aNM4B2q09XJrbRQk-4J8GuFGDFg8gLp3EVpC/dev");
//                    HttpGet post = new HttpGet("https://kamorris.com/lab/abp/booksearch.php");
//
//
//
//                    post.setHeader(HTTP.CONTENT_TYPE, "application/json");
//
//                    response = client.execute(post);
//                    int responseCodeDeal=response.getStatusLine().getStatusCode();
//                    Log.e("responseCodeDeal", String.valueOf(responseCodeDeal));
//
////                        Config.Toast(context,"responseCodeMerchantPin :"+responseCodeDeal);
//                    /*Checking response */
//                    InputStream inpts=null;
//                    if(response!=null){
//                        inpts = response.getEntity().getContent(); //Get the data in the entity
//                    }
//
//                    BufferedReader br = new BufferedReader(new InputStreamReader(inpts));
//                    StringBuilder sb = new StringBuilder();
//
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                    inpts.close();
//                    String  result = sb.toString();
//                    Log.e("json ",result);
//
//
//                    if (responseCodeDeal==200){
//                        JSONArray json = new JSONArray(result);
//// ...
//
//                        for(int i=0;i<json.length();i++){
//                            JSONObject e = json.getJSONObject(i);
//
//                            book=new Book();
//                            book_id=e.getString("book_id");
//                             author=e.getString("author");
//                            title=e.getString("title");
//                            cover_url=e.getString("cover_url");
//                            book.setAuthor(author);
//                            book.setBook_id(book_id);
//                            book.setTitle(title);
//                            book.setCover_url(cover_url);
//                        booksArray.add(book);
//                        }
//                        adapter=new MyListAdapter(MainActivity.this, booksArray);
//                        Log.e("booksArray ",booksArray.toString());
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
//                                transaction.add(R.id.frame_container, new BookListFragment());
////        transaction.addToBackStack(null);
//                                transaction.commit();
//
//                            }
//                        });
//                    }
//
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
////        @Override
////        protected void onPostExecute(String result) {
//////            progressDialog.dismiss();
////            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
////            transaction.replace(R.id.frame_container, new BookListFragment());
//////        transaction.addToBackStack(null);
////            transaction.commit();
////
////        }
//    }
}
