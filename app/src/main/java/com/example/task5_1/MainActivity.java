package com.example.task5_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerViewTopStories;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewAdapterTopStories recyclerViewAdapterTopStories;

    FragmentCommunication communication=new FragmentCommunication() {
        @Override
        public void respond(News news, List<News> relatedNews, Context context) {
            NewsFragment fragmentB= NewsFragment.newInstance(context);
            Bundle bundle=new Bundle();
            bundle.putSerializable("News",news);
            bundle.putSerializable("RelatedNews",(Serializable) relatedNews);
            fragmentB.setArguments(bundle);
            FragmentManager manager=getFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.linearlayout,fragmentB).commit();

            Log.e("mainac","mainac");

        }

    };

    public void createTopStoriesView(ArrayList<News> newsArrayList){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewTopStories = (RecyclerView) findViewById(R.id.recyclerViewTopStories);
        recyclerViewTopStories.setLayoutManager(layoutManager);
        recyclerViewAdapterTopStories = new RecyclerViewAdapterTopStories(newsArrayList, this);
        recyclerViewTopStories.setAdapter(recyclerViewAdapterTopStories);
    }
    public void createView(ArrayList<News> newsArrayList){
//        Log.e("news", newsArrayList.toString());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(newsArrayList, this, communication);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
    public void fetchData(String value){
        String url = "https://newsapi.org/v2/top-headlines?"+value+"&apiKey=ad8d1ad1212f4b45b27edbae9d858b93";

        RequestQueue queue = Volley.newRequestQueue(this);
        ArrayList<News> newsArrayList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject  response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("articles");
                                    for(int i = 0 ; i <jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        JSONObject sourceObject = jsonArray.getJSONObject(i).getJSONObject("source");
                                        News news = new News(
                                                jsonObject.getString("title"),
                                                jsonObject.getString("content"),
                                                jsonObject.getString("urlToImage"),
                                                sourceObject.getString("name"),
                                                jsonObject.getString("description")
                                        );
//                                        Log.e(""+i, news.toString());
                                        if(!news.anyNull()) {
                                            newsArrayList.add(news);
                                        }
                                    }
                                    createView(newsArrayList);
                                    createTopStoriesView(newsArrayList);
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "Volley JSON Error",
                                            Toast.LENGTH_SHORT).show();
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Volley Error",
                                Toast.LENGTH_SHORT).show();
                        Log.e("api", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> mHeaders = new ArrayMap<String, String>();
                mHeaders.put("User-Agent", "Mozilla/5.0");
                return mHeaders;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData("country=us");
    }

}