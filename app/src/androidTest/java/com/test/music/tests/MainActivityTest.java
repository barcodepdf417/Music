package com.test.music.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.test.music.activity.MainActivity;
import org.junit.Test;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testExercise01() throws Exception {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
        solo.clickOnText("1");
        solo.scrollToSide(Solo.RIGHT);
        solo.sleep(1000);
        solo.scrollToSide(Solo.RIGHT);
        solo.sleep(1000);
        solo.scrollToSide(Solo.RIGHT);
        solo.goBack();
        solo.sleep(1000);

    }
}