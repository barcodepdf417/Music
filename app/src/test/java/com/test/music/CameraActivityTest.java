package com.test.music;

import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import com.test.music.R;
import com.test.music.activity.CameraActivity;
import com.test.music.activity.MainActivity;
import com.test.music.activity.Preview;
import com.test.music.MusicRobolectricRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowCamera;
import org.robolectric.shadows.ShadowToast;
import java.io.IOException;

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

    @After
    public void tearDown() throws Exception {
        ShadowCamera.clearCameraInfo();
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

    @Test
    public void testStartStopPreview() throws InterruptedException {
        camera.startPreview();
        assertEquals(shadowCamera.isPreviewing(), true);
        camera.stopPreview();
        assertEquals(shadowCamera.isPreviewing(), false);
    }

    @Test
    public void testPreview() throws InterruptedException, IOException {
        SurfaceView surfaceView = (SurfaceView)cameraActivity.findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        preview = new Preview(cameraActivity, surfaceView);
        preview.setCamera(camera);
        assertNotNull(preview);

        camera.startPreview();
        camera.setPreviewDisplay(surfaceHolder);
        assertEquals(shadowCamera.getPreviewDisplay(), surfaceHolder);

        assertEquals(shadowCamera.isReleased(), false);
        camera.release();
        assertEquals(shadowCamera.isReleased(), true);
    }

    @Test
    public void testRelease() throws Exception {
        assertEquals(shadowCamera.isReleased(), false);
        camera.release();
        assertEquals(shadowCamera.isReleased(), true);
    }

    @Test
     public void testUnlock() throws Exception {
        camera.unlock();
        assertEquals(shadowCamera.isLocked(), false);
        camera.reconnect();
        assertEquals(shadowCamera.isLocked(), true);
    }

    @Test
    public void testTakePicture() throws Exception {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    @Test
    public void testSetDisplayOrientationUpdatesCameraInfos() {
        addBackCamera();
        addFrontCamera();

        camera = Camera.open(1);
        camera.setDisplayOrientation(180);

        Camera.CameraInfo cameraQuery = new Camera.CameraInfo();
        Camera.getCameraInfo(ShadowCamera.getLastOpenedCameraId(), cameraQuery);
        assertEquals(cameraQuery.orientation, 180);
    }


    private void addBackCamera() {
        Camera.CameraInfo backCamera = new Camera.CameraInfo();
        backCamera.facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        backCamera.orientation = 0;
        ShadowCamera.addCameraInfo(0, backCamera);
    }

    private void addFrontCamera() {
        Camera.CameraInfo frontCamera = new Camera.CameraInfo();
        frontCamera.facing = Camera.CameraInfo.CAMERA_FACING_FRONT;
        frontCamera.orientation = 90;
        ShadowCamera.addCameraInfo(1, frontCamera);
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            Log.d("CameraActivityTest", "onShutter'd");
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d("CameraActivityTest", "onPictureTaken - raw");
        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d("CameraActivityTest", "onPictureTaken - jpeg");
        }
    };

}
