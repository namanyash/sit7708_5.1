package com.example.task5_1;

import android.content.Context;

import java.util.List;

public interface FragmentCommunication {
    void respond(News news, List<News> relatedNews, Context context);
}