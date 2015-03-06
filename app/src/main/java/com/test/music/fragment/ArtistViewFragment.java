package com.test.music.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.music.R;
import com.test.music.pojo.Album;
import com.test.music.pojo.AlbumsHolder;

import java.util.List;

public class ArtistViewFragment extends Fragment{
    public static final String BUNDLE = "bundle";
    private static final String ALBUMS = "albums";

    private String name;
    private FragmentActivity myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyPagerAdapter myPagerAdapter;
        View viewRoot = inflater.inflate(R.layout.pager_view, container, false);

        Bundle bundle = this.getArguments();
        AlbumsHolder holder = null;
        if(bundle != null){
            Bundle newBundle = bundle.getBundle(BUNDLE);
            holder = (AlbumsHolder)newBundle.getSerializable(ALBUMS);
        }
        ViewPager viewPager = (ViewPager) viewRoot.findViewById(R.id.myviewpager);
        if(!holder.getAlbums().isEmpty()){
            myPagerAdapter = new MyPagerAdapter(holder.getAlbums());
        } else {
            myPagerAdapter = new MyPagerAdapter(new Album());
        }
        viewPager.setAdapter(myPagerAdapter);

        return viewRoot;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class MyPagerAdapter extends PagerAdapter {

        private String name;
        private List<Album> albumsList;
        private Album emptyAlbum;

        private MyPagerAdapter(List<Album> albums) {
            this.albumsList = albums;
        }

        private MyPagerAdapter(Album album) {
            this.emptyAlbum = album;
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

            LinearLayout layout = new LinearLayout(myContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(0xFF101010);
            layout.setLayoutParams(layoutParams);
            layout.setGravity(Gravity.CENTER);

            if(emptyAlbum != null){
                TextView textView = new TextView(myContext);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(30);
                textView.setGravity(Gravity.CENTER);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.setText("There are no albums for this artist");
                layout.addView(textView);

            } else {
                TextView idView = new TextView(myContext);
                idView.setTextColor(Color.WHITE);
                idView.setTextSize(30);
                idView.setGravity(Gravity.CENTER);
                idView.setTypeface(Typeface.DEFAULT_BOLD);
                idView.setText(Integer.toString(albumsList.get(position).getId()));

                TextView titleView = new TextView(myContext);
                titleView.setTextColor(Color.WHITE);
                titleView.setTextSize(30);
                titleView.setGravity(Gravity.CENTER);
                titleView.setTypeface(Typeface.DEFAULT_BOLD);
                titleView.setText(albumsList.get(position).getTitle() );

                TextView type = new TextView(myContext);
                type.setTextColor(Color.WHITE);
                type.setTextSize(30);
                type.setGravity(Gravity.CENTER);
                type.setTypeface(Typeface.DEFAULT_BOLD);
                type.setText(albumsList.get(position).getType() );

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
