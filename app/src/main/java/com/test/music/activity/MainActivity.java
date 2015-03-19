package com.test.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.test.music.R;
import com.test.music.events.OnUpdateListener;
import com.test.music.fragment.AlbumsFragment;
import com.test.music.fragment.ArtistsFragment;

public class MainActivity extends FragmentActivity  implements OnUpdateListener{
    public static final String ARTIST = "artist";
    public static final String BUNDLE = "bundle";

    public boolean mTwoPane;
    private ArtistsFragment forecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.albums_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.albums_container, new AlbumsFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        forecastFragment =  ((ArtistsFragment)getSupportFragmentManager()
                .findFragmentById(R.id.recycler_fragment));
        forecastFragment.setUseTwoPane(mTwoPane);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdate(String msg, Bundle extract) {
        if (mTwoPane) {
            AlbumsFragment fragment = new AlbumsFragment();
            Bundle bundle = new Bundle();
            bundle.putBundle(BUNDLE,extract);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.albums_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            AlbumsFragment fragment = new AlbumsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST, msg);
            bundle.putBundle(BUNDLE,extract);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recycler_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void createPhoto(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}
