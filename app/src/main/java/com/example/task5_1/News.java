package com.example.task5_1;

import android.media.Image;
import android.net.Uri;

import java.io.Serializable;

public class News implements Serializable {
    private String Title;
    private String content;
    private String uri;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private String description;

    public News(String title, String content, String uri, String source, String description) {
        Title = title;
        this.content = content;
        this.uri = uri;
        this.source = source;
        this.description = description;
    }

    @Override
    public String toString() {
        return "News{" +
                "Title='" + Title + '\'' +
                ", content='" + content + '\'' +
                ", uri='" + uri + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean anyNull(){
        if(this.Title == "null" || this.content == "null" || this.uri == "null" || this.description == "null" || this.source == "null"){
            return true;
        }
        return false;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
