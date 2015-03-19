package com.test.music;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.os.Bundle;
import com.test.music.R;
import com.test.music.activity.PhotoActivity;
import com.test.music.activity.CameraActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;

import org.robolectric.annotation.Config;
import org.robolectric.util.Transcript;
import com.test.music.MusicRobolectricRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
@Config(emulateSdk = 18)
@RunWith(MusicRobolectricRunner.class)
public class PhotoActivityTest {

    private PhotoActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(PhotoActivity.class);
    }

    @Test
    public void testAlbumsFragment() throws Exception {
        assertNotNull(activity);
        ImageView image = (ImageView) activity.findViewById(R.id.image);
        assertNotNull(image);
    }

    @Test
    public void testStartPhotoActivityFromResult() throws Exception {
        final Transcript transcript = new Transcript();
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        CameraActivity activity = new CameraActivity() {
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                Intent i = new Intent(this, PhotoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(URI, data.getData());
                i.putExtra(BUNDLE, bundle);
                startActivity(i);
            }
        };

        //pick image from gallery
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, 1);

        Robolectric.shadowOf(activity).receiveResult(i, Activity.RESULT_OK,
                new Intent().setData(Uri.parse("content:foo")));

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(startedIntent);
    }

}
