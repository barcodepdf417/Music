package com.test.music.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.test.music.activity.CameraActivity;
import org.junit.Test;

public class CameraActivityTest extends ActivityInstrumentationTestCase2<CameraActivity> {
    public CameraActivityTest() {
        super(CameraActivity.class);
    }

    private Solo solo;

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testExercise01() throws Exception {
        solo.assertCurrentActivity("wrong activity", CameraActivity.class);
        assertTrue(solo.waitForText("Photo created", 1, 5000));
    }
}