package com.test.music.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.music.R;
import com.test.music.adapter.AlbumAdapter;
import com.test.music.pojo.Album;
import com.test.music.pojo.AlbumsHolder;

public class AlbumsFragment extends Fragment{
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
        AlbumAdapter myPagerAdapter;
        View viewRoot = inflater.inflate(R.layout.pager_view, container, false);

        Bundle bundle = this.getArguments();
        AlbumsHolder holder = null;
        if(bundle != null){
            Bundle newBundle = bundle.getBundle(BUNDLE);
            holder = (AlbumsHolder)newBundle.getSerializable(ALBUMS);
        }
        ViewPager viewPager = (ViewPager) viewRoot.findViewById(R.id.myviewpager);
        if(!holder.getAlbums().isEmpty()){
            myPagerAdapter = new AlbumAdapter(holder.getAlbums(), myContext);
        } else {
            myPagerAdapter = new AlbumAdapter(new Album(), myContext);
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
