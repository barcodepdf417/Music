package com.test.music;

import android.content.Intent;
import android.hardware.Camera;
import android.view.SurfaceView;
import android.widget.Button;

import com.test.music.R;
import com.test.music.activity.CameraActivity;
import com.test.music.activity.MainActivity;
import com.test.music.activity.Preview;
import com.test.music.MusicRobolectricRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowCamera;
import org.robolectric.shadows.ShadowToast;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Config(emulateSdk = 18)
@RunWith(MusicRobolectricRunner.class)
public class CameraActivityTest {

    private CameraActivity cameraActivity;
    private MainActivity mainActivity;
    private Camera camera;
    private ShadowCamera shadowCamera;
    private Preview preview;

    @Before
    public void setUp() throws Exception {
        camera = Camera.open();
        shadowCamera = Robolectric.shadowOf(camera);
        cameraActivity = Robolectric.setupActivity(CameraActivity.class);
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testPreview() throws InterruptedException {
        preview = new Preview(cameraActivity, (SurfaceView)cameraActivity.findViewById(R.id.surfaceView));
        assertNotNull(preview);
    }

    @Test
    public void testOpenCameraActivityButton() throws InterruptedException {
        assertThat(mainActivity).isNotNull();

        Button makePhoto = (Button) mainActivity.findViewById(R.id.makePhoto);
        assertThat(makePhoto).isNotNull();
        makePhoto.performClick();

        Intent intent = Robolectric.shadowOf(cameraActivity).peekNextStartedActivity();
        assertEquals(CameraActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testCreatePhotoButton(){
        Button tryAgain = (Button) cameraActivity.findViewById(R.id.buttonId);

        assertEquals(ShadowToast.getTextOfLatestToast(), "Photo will be created in 5 sec");
        tryAgain.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast(), "Timer is already started");
    }

}
