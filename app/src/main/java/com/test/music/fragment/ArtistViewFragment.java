package com.test.music.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.music.R;
import com.test.music.retrofit.ArtistResponse;
import com.test.music.retrofit.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArtistViewFragment extends Fragment{
    public static final String UNNAMED = "unnamed";
    private final String ARTIST = "artist";

    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;
    private View viewRoot;
    private FragmentActivity myContext;
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.pager_view, container, false);

        Bundle bundle = this.getArguments();
        String name = UNNAMED;
        if(bundle != null){
            name = bundle.getString(ARTIST, UNNAMED);
        }
        viewPager = (ViewPager)viewRoot.findViewById(R.id.myviewpager);
        myPagerAdapter = new MyPagerAdapter(name);
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

        private MyPagerAdapter(String name) {
            this.name = name;
        }

        private String name;
        int NumberOfPages = 5;

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            RestClient.get().getJournal("data.json", new Callback<ArtistResponse>() {
                @Override
                public void success(ArtistResponse artistResponse, Response response) {
                    Toast.makeText(myContext,
                            "Base " +artistResponse.getAlbums() + " clicked " + artistResponse.getArtists() + " title ",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("App", error.toString());
                }
            });

            TextView textView = new TextView(myContext);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setText(getName() + " " + String.valueOf(position));

            LinearLayout layout = new LinearLayout(myContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(0xFF101010);
            layout.setLayoutParams(layoutParams);
            layout.setGravity(Gravity.CENTER);
            layout.addView(textView);

//            final int page = position;
//            layout.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(myContext,
//                            "Page " + page + " clicked",
//                            Toast.LENGTH_LONG).show();
//                }});

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
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(String msg){
        setName(msg);
    }
}
