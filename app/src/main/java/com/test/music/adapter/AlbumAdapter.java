package com.test.music.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.music.pojo.Album;

import java.util.List;

public class AlbumAdapter extends PagerAdapter {

    private String name;
    private List<Album> albumsList;
    private Album emptyAlbum;
    private Context context;

    public AlbumAdapter(List<Album> albums, Context context) {
        this.albumsList = albums;
        this.context = context;
    }

    public AlbumAdapter(Album album, Context context) {
        this.emptyAlbum = album;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(albumsList != null && albumsList.isEmpty() || albumsList == null){
            return 1;
        }
        return albumsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setBackgroundColor(0xFF101010);
        layout.setLayoutParams(layoutParams);
        layout.setGravity(Gravity.CENTER);

        if(emptyAlbum != null){
            TextView textView = new TextView(context);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setText("There are no albums for this artist");
            layout.addView(textView);

        } else {
            TextView idView = new TextView(context);
            idView.setTextColor(Color.WHITE);
            idView.setTextSize(30);
            idView.setGravity(Gravity.CENTER);
            idView.setTypeface(Typeface.DEFAULT_BOLD);
            idView.setText(Integer.toString(albumsList.get(position).getId()));

            TextView titleView = new TextView(context);
            titleView.setTextColor(Color.WHITE);
            titleView.setTextSize(30);
            titleView.setTextColor(Color.RED);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTypeface(Typeface.DEFAULT_BOLD);
            titleView.setText(albumsList.get(position).getTitle());

            TextView type = new TextView(context);
            type.setTextColor(Color.WHITE);
            type.setTextSize(30);
            type.setTextColor(Color.BLUE);
            type.setGravity(Gravity.CENTER);
            type.setTypeface(Typeface.DEFAULT_BOLD);
            type.setText(albumsList.get(position).getType());

            layout.addView(idView);
            layout.addView(titleView);
            layout.addView(type);
        }
        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbumsList() {
        return albumsList;
    }

    public void setAlbumsList(List<Album> albumsList) {
        this.albumsList = albumsList;
    }
}