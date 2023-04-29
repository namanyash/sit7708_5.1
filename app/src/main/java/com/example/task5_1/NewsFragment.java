package com.example.task5_1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class NewsFragment extends Fragment {

    News news;
    List<News> relatedNews;

    ImageView imageView;
    RecyclerView recyclerViewRelated;
    TextView content;
    TextView description;
    TextView title;
    private Context context;

    Button backButton;

    public static NewsFragment newInstance(Context context) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.context = context;
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news=(News)getArguments().getSerializable("News");
        relatedNews=(List<News>) getArguments().getSerializable("RelatedNews");
        Log.e("onCreate","onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("onCreateView",news.toString());

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        imageView = view.findViewById(R.id.newsImage);
        recyclerViewRelated = view.findViewById(R.id.recyclerViewRelated);
        content = view.findViewById(R.id.content);
        description = view.findViewById(R.id.description);
        title = view.findViewById(R.id.title);
        backButton = view.findViewById(R.id.backButton);
        title.setText(news.getTitle());
        description.setText(news.getDescription());
        content.setText(news.getContent());
        new DownloadImageTask(imageView).execute(news.getUri());

        recyclerViewRelated = view.findViewById(R.id.recyclerViewRelated);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(relatedNews, context, communication);
        recyclerViewRelated.setAdapter(recyclerViewAdapter);
        recyclerViewRelated.setLayoutManager(new GridLayoutManager(context,1));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent mStartActivity = new Intent(context, MainActivity.class);
                startActivity(mStartActivity);
            }
        });

        return view;
    }
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
        }

    };
}