package com.example.bookshelf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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
import java.util.HashMap;

import static com.example.bookshelf.MainActivity.adapter;
import static com.example.bookshelf.MainActivity.booksArray;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String[] maintitle ={
            "Title 1","Title 2",
            "Title 3","Title 4",
            "Title 5",
    };
    public static  ListView list;
    Activity context;
    public static String Author="",Title="",cover_url="";

//   HashMap<Integer,String> author=new HashMap<Integer,String>();
//   HashMap<Integer,String> title=new HashMap<Integer,String>();
    ArrayList<HashMap> books=new ArrayList<HashMap>();
    public static   FrameLayout frame_details;
    EditText edEdit;
    Button btnSearch;
    Book book;
    String search="";
    String book_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view=    inflater.inflate(R.layout.fragment_book_list, container, false);
     list=view.findViewById(R.id.list);
        edEdit=view.findViewById(R.id.edEdit);
     list=view.findViewById(R.id.list);
     btnSearch=view.findViewById(R.id.btnSearch);

//        books();
    context=getActivity();



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksArray.clear();
                search=edEdit.getText().toString();
                new SendRequest().execute();

            }
        });


//        MyListAdapter adapter=new MyListAdapter(context, maintitle);
//        MyListAdapter adapter=new MyListAdapter(context, books);
//        Log.e("MainActivity.booksArray", booksArray.get(0).getAuthor());
//        list.setAdapter(adapter);


        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    public class SendRequest extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog=new ProgressDialog(context);


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPreExecute() {
//            progressDialog.setMessage("Loading please wait......");
//            progressDialog.show();
            booksArray.clear();
            adapter=null;
        }

        protected String doInBackground(String... arg0) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 20000); //Timeout Limit
                HttpResponse response;
                try {


//                    HttpGet post = new HttpGet("https://script.google.com/macros/s/AKfycbx-7xFa0tpDYiY9aNM4B2q09XJrbRQk-4J8GuFGDFg8gLp3EVpC/dev");
                    HttpGet post;


                     post = new HttpGet("https://kamorris.com/lab/abp/booksearch.php?search="+search);



                    post.setHeader(HTTP.CONTENT_TYPE, "application/json");

                    response = client.execute(post);
                    int responseCodeDeal=response.getStatusLine().getStatusCode();
                    Log.e("responseCodeDeal", String.valueOf(responseCodeDeal));

//                        Config.Toast(context,"responseCodeMerchantPin :"+responseCodeDeal);
                    /*Checking response */
                    InputStream inpts=null;
                    if(response!=null){
                        inpts = response.getEntity().getContent(); //Get the data in the entity
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(inpts));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inpts.close();
                    String  result = sb.toString();
                    Log.e("json ",result);


                    if (responseCodeDeal==200) {
                        JSONArray json = new JSONArray(result);
// ...

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject e = json.getJSONObject(i);

                            book = new Book();
                            book_id = e.getString("book_id");
                            Author = e.getString("author");
                            Title = e.getString("title");
                            cover_url = e.getString("cover_url");
                            book.setAuthor(Author);
                            book.setBook_id(book_id);
                            book.setTitle(Title);
                            book.setCover_url(cover_url);
                            booksArray.add(book);
                        }
                            adapter = new MyListAdapter(context, booksArray);
                            Log.e("booksArray ", booksArray.toString());
                            if (adapter!=null) {

                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    list.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

//        @Override
//        protected void onPostExecute(String result) {
////            progressDialog.dismiss();
//            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_container, new BookListFragment());
////        transaction.addToBackStack(null);
//            transaction.commit();
//
//        }
    }



//    public void books(){
//        author.put(0,"half girlfriend");
//        title.put(0,"Chetan Bhagat");
//        books.add(author);
//        books.add(title);
//        author.put(1,"the white tiger");
//        title.put(1,"Arvind Adiga");
//        books.add(title);
//        books.add(author);
//        author.put(2,"War and Peace");
//        title.put(2,"Leo Tolstoy");
//        books.add(title);
//        books.add(author);
//        author.put(3,"In Search of Lost Time");
//        title.put(3,"Marcel Proust");
//        books.add(title);
//        books.add(author);
//        author.put(4,"The Great Gatsby");
//        title.put(4,"F. Scott Fitzgerald");
//        books.add(title);
//        books.add(author);
//
//        author.put(5,"Javier Moro");
//        title.put(5,"The Red Sari");
//        books.add(title);
//        books.add(author);
//
//        author.put(6,"Dalai Lama");
//        title.put(6,"Freedom in Exile");
//        books.add(title);
//        books.add(author);
//        author.put(7,"Ruskin Bond");
//        title.put(7,"My Favourite Nature Stories");
//        books.add(title);
//        books.add(author);
//
//        author.put(7,"Khurshid M Kasuari");
//        title.put(7,"Neither a hawk nor a dove");
//        books.add(title);
//        books.add(author);
//
//        author.put(8,"Deepak Nayyar");
//        title.put(8,"Faces and Places Professor");
//        books.add(title);
//        books.add(author);
//
//        author.put(9,"Meira Kumar");
//        title.put(9,"Indian Parliamentary Diplomacy");
//        books.add(title);
//        books.add(author);
//
//        author.put(10,"Farishta");
//        title.put(10,"Kapil Isapuari");
//        books.add(title);
//        books.add(author);
//
//        author.put(11,"Amitabh Ghosh");
//        title.put(11,"Flood of fire");
//        books.add(title);
//        books.add(author);
//
//
//
//
//    }


}
