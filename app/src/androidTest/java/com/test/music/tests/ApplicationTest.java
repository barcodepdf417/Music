package com.test.music.tests;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;

import com.test.music.activity.MainActivity;
import com.test.music.exception.MyErrorHandler;
import com.test.music.R;
import com.test.music.fragment.RecyclerViewFragment;
import com.test.music.pojo.Album;
import com.test.music.pojo.Artist;
import com.test.music.retrofit.ArtistResponse;
import com.test.music.retrofit.LocalJsonClient;
import com.test.music.retrofit.RestClient;

import retrofit.RestAdapter;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mFirstTestActivity;
    private RestClient.Api api;

    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        mFirstTestActivity = getActivity();
    }

    public void testJson() {
        ArtistResponse response = getResonse();
        Artist artist = getArtistMock();
        Album album = getAlbumMock();
        assertEquals(response.getArtists().get(0).getName(), artist.getName());
        assertEquals(response.getArtists().get(0).getGenres(), artist.getGenres());
        assertEquals(response.getArtists().get(0).getDescription(), artist.getDescription());

        assertEquals(response.getAlbums().get(0).getId(), album.getId());
        assertEquals(response.getAlbums().get(0).getTitle(), album.getTitle());
        assertEquals(response.getAlbums().get(0).getType(), album.getType());
    }

    public void testFragment() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        startFragment(fragment);
        assertNotNull("RecyclerViewFragment is null", fragment);
    }

    private ArtistResponse getResonse(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("sample.com")
                .setClient(new LocalJsonClient())
                .setErrorHandler(new MyErrorHandler())
                .build();
        api = adapter.create(RestClient.Api.class);
        return api.getData();
    }

    private Artist getArtistMock(){
        Artist artist = new Artist();
        artist.setName("Inioth");
        artist.setGenres("pop,rock");
        artist.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam varius nunc quis est ullamcorper, sed vestibulum enim egestas. Nulla nunc ante, cursus vitae leo nec, mattis viverra dolor. Suspendisse lobortis ipsum id dolor pretium ultricies. Sed ac neque sed mauris fringilla accumsan ac et velit. Aliquam rhoncus, turpis sed semper mollis, neque purus cursus nisl, tempor vehicula ligula quam id arcu. Cras eros diam, commodo id placerat eu, iaculis nec nisl. In vestibulum rhoncus vulputate. Mauris pharetra consequat tristique. Vestibulum a ante a justo consectetur feugiat nec et velit. Pellentesque suscipit massa eget tortor scelerisque bibendum. Vivamus eu pellentesque metus. Proin faucibus justo est, ut ultrices mauris ultrices ac. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;");
        return artist;
    }

    private Album getAlbumMock(){
        Album album = new Album();
        album.setId(1);
        album.setTitle("Cour Plunder");
        album.setType("single");
        return album;
    }

    private Fragment startFragment(Fragment fragment) {
        FragmentTransaction transaction = mFirstTestActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.recycler_fragment, fragment, "tag");
        transaction.commit();
        getInstrumentation().waitForIdleSync();
        Fragment frag = mFirstTestActivity.getSupportFragmentManager().findFragmentByTag("tag");
        return frag;
    }
}