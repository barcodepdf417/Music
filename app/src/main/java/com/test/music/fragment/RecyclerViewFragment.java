package com.test.music.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.music.R;
import com.test.music.adapter.ArtistAdapter;
import com.test.music.events.OnItemClickListener;
import com.test.music.events.OnUpdateListener;
import com.test.music.pojo.Artist;
import com.test.music.retrofit.ArtistResponse;
import com.test.music.retrofit.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RecyclerViewFragment extends Fragment implements View.OnClickListener{

    private FragmentActivity myContext;
    private boolean useTwoPane;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private OnUpdateListener updateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = LayoutInflater.from(myContext).inflate(R.layout.recycler_view, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        (new AsyncListLoader()).execute();

        return rootView;
    }

    private class AsyncListLoader extends AsyncTask<Void, Void, Void> {
        List<Artist> result = new ArrayList<>() ;

        @Override
        protected void onPostExecute(Void args) {

            artistAdapter = new ArtistAdapter(result);
            recyclerView.setAdapter(artistAdapter);

            Log.d("RecyclerViewFragment", "RecyclerViewFragment onPostExecute" + artistAdapter.getItemCount() + "         " + artistAdapter.getArtistList().size());
            Log.d("RecyclerViewFragment", "RecyclerViewFragment onPostExecute result" + artistAdapter.getItemCount() + "         " + result.size());

            artistAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView textView = (TextView)view.findViewById(R.id.txtName);
                    ((OnUpdateListener)getActivity())
                            .onUpdate(textView.getText().toString());
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for(int i = 0; i < 100; i++){
                RestClient.get().getJournal("data.json", new Callback<ArtistResponse>() {
                    @Override
                    public void success(ArtistResponse artistResponse, Response response) {
                        result.addAll(artistResponse.getArtists());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("App", error.toString());
                    }
                });
            }
            Log.d("RecyclerViewFragment", "RecyclerViewFragment doInBackground result" + result.size());

            return null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        myContext=(FragmentActivity) activity;
        try {
            updateListener = (OnUpdateListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("RecyclerViewFragment", "onClick " + v.getId());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setUseTwoPane(boolean useTwoPane) {
        this.useTwoPane = useTwoPane;
    }
}
