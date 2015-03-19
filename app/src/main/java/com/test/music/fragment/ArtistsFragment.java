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
import com.test.music.exception.MyErrorHandler;
import com.test.music.pojo.Album;
import com.test.music.pojo.AlbumsHolder;
import com.test.music.pojo.Artist;
import com.test.music.retrofit.ArtistResponse;
import com.test.music.retrofit.LocalJsonClient;
import com.test.music.retrofit.RestClient;
import com.test.music.retrofit.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

public class ArtistsFragment extends Fragment implements View.OnClickListener{
    public static final String ALBUMS = "albums";
    public static RestClient.Api serviceWrapper;

    private FragmentActivity myContext;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    private OnUpdateListener updateListener;
    private ProgressDialog progressDialog;
    private boolean useTwoPane;

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

    public RestClient.Api getServiceWrapper() {
        if (serviceWrapper == null) {
            ArtistsFragment.setServiceWrapper(new RestService().getService());
        }
        return serviceWrapper;
    }

    public static void setServiceWrapper(RestClient.Api serviceWrapper) {
        ArtistsFragment.serviceWrapper = serviceWrapper;
    }


    private class AsyncListLoader extends AsyncTask<Void, Void, Void> {
        List<Artist> result = new ArrayList<Artist>() ;
        List<Album> albumList = new ArrayList<Album>() ;

        @Override
        protected void onPostExecute(Void args) {
            artistAdapter = new ArtistAdapter(result);
            recyclerView.setAdapter(artistAdapter);

            artistAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView id = (TextView) view.findViewById(R.id.txtId);
                    Bundle bundle = new Bundle();
                    long artistId = Long.parseLong(id.getText().toString());
                    bundle.putSerializable(ALBUMS, new AlbumsHolder(prepareList(artistId, albumList)));
                    ((OnUpdateListener) getActivity()).onUpdate(id.getText().toString(), bundle);
                }
            });
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(myContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArtistResponse response;
            try {
                response = getService().getData();
            } catch (RuntimeException ex){
                response = getResonse();
            }
            result.addAll(response.getArtists());
            albumList.addAll(response.getAlbums());
            return null;
        }

//        @Override
//        protected Void doInBackground(Void... params) {
//            ArtistResponse response = getService().getData();
//            result.addAll(response.getArtists());
//            albumList.addAll(response.getAlbums());
//            return null;
//        }

        private ArtistResponse getResonse(){
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("sample.com")
                    .setClient(new LocalJsonClient())
                    .setErrorHandler(new MyErrorHandler())
                    .build();
            return adapter.create(RestClient.Api.class).getData();
        }
    }

    public RestClient.Api getService(){
        return new RestService().getService();
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

    private List<Album> prepareList(long id, List<Album> albums){
        List<Album> result = new ArrayList<Album>();
        for(Album album : albums){
            if(album.getArtistId() == id){
                result.add(album);
            }
        }
        return result;
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
