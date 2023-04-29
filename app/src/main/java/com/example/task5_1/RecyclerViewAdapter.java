package com.example.task5_1;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task5_1.News;
import com.example.task5_1.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;
    private FragmentCommunication mCommunicator;


    public RecyclerViewAdapter(List<News> newsList, Context context, FragmentCommunication communication) {
        this.newsList = newsList;
        this.context = context;
        this.mCommunicator = communication;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);

        return new ViewHolder(itemView,mCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            holder.title.setText(newsList.get(position).getSource());
            holder.introLine.setText(newsList.get(position).getTitle());
            new DownloadImageTask(holder.imageView).execute(newsList.get(position).getUri());

            NewsFragment newsFragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title",newsList.get(position).getTitle());
            bundle.putString("content",newsList.get(position).getContent());
            newsFragment.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView title;
        TextView introLine;

        FragmentCommunication communication;

        public ViewHolder(@NonNull View itemView, FragmentCommunication Communicator) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.title = itemView.findViewById(R.id.title);;
            this.introLine = itemView.findViewById(R.id.introLine);;
            this.communication=Communicator;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Log.e("here","here");
            communication.respond(newsList.get(getAdapterPosition()),newsList, context);
        }
    }

}
