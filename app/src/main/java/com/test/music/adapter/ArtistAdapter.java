package com.test.music.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.music.R;
import com.test.music.events.OnItemClickListener;
import com.test.music.pojo.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> artistList;
    OnItemClickListener mItemClickListener;

    public ArtistAdapter(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @Override
    public int getItemCount() {
        if(artistList != null){
            return artistList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder artistViewHolder, int i) {
        Artist artist = artistList.get(i);
        artistViewHolder.vName.setText(artist.getName());
        artistViewHolder.currentArtist = artistList.get(i);
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ArtistViewHolder(itemView);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView vName;
        public TextView id;
        public Artist currentArtist;

        public ArtistViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getPosition());
        }
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }
}