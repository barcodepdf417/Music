package com.test.music;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.test.music.activity.CameraActivity;
import com.test.music.activity.MainActivity;
import com.test.music.fragment.AlbumsFragment;
import com.test.music.pojo.Album;
import com.test.music.pojo.AlbumsHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.Join;
import org.robolectric.util.Transcript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.test.music.MusicRobolectricRunner;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.util.FragmentTestUtil.startFragment;

@Config(emulateSdk = 18)
@RunWith(MusicRobolectricRunner.class)
public class MainActivityTest {

    private Transcript transcript;
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
        transcript = new Transcript();
        Robolectric.getShadowApplication().getBackgroundScheduler().pause();
        Robolectric.getUiThreadScheduler().pause();
    }

    @Test
    public void testAlbumsFragment() throws Exception {
        AlbumsFragment albumsFragment = new AlbumsFragment();
        prepareDataForFragment(albumsFragment);
        startFragment(albumsFragment);
        assertNotNull(albumsFragment);
    }

    private void prepareDataForFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        Bundle serializableBundle = new Bundle();
        List<Album> albums = new ArrayList<Album>();
        albums.add(new Album());
        AlbumsHolder holder = new AlbumsHolder(albums);
        serializableBundle.putSerializable("albums", holder);
        bundle.putBundle("bundle", serializableBundle);
        fragment.setArguments(bundle);
    }

    @Test
    public void checkRecyclerViewNotNullAndHaveChild() throws Exception {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.cardList);
        assertNotNull(recyclerView);

        assertEquals(recyclerView.getChildCount(), 11);

        recyclerView.addView(new CardView(activity), 0);
        recyclerView.addView(new CardView(activity), 1);

        assertEquals(recyclerView.getChildCount(), 13);
        assertNotNull(recyclerView.getChildAt(1));
    }

    @Test
    public void testOpenCameraActivity() {
        assertThat(activity).isNotNull();

        Button makePhoto = (Button) activity.findViewById(R.id.makePhoto);
        assertThat(makePhoto).isNotNull();
        makePhoto.performClick();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertEquals(CameraActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testSimpleAsynkTask() throws Exception {
        AsyncTask<String, String, String> asyncTask = new TestAsyncTask();

        asyncTask.execute("a", "b");
        transcript.assertEventsSoFar("onPreExecute");

        Robolectric.runBackgroundTasks();
        transcript.assertEventsSoFar("doInBackground a, b");
        assertEquals("Result should get stored in the AsyncTask", "c", asyncTask.get(100, TimeUnit.MILLISECONDS));

        Robolectric.runUiThreadTasks();
        transcript.assertEventsSoFar("onPostExecute c");
    }

    private class TestAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            transcript.add("onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            transcript.add("doInBackground " + Join.join(", ", (Object[]) strings));
            return "c";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            transcript.add("onProgressUpdate " + Join.join(", ", (Object[]) values));
        }

        @Override
        protected void onPostExecute(String s) {
            transcript.add("onPostExecute " + s);
        }

        @Override
        protected void onCancelled() {
            transcript.add("onCancelled");
        }
    }
}
