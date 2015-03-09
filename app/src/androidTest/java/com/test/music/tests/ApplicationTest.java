package com.test.music.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.test.music.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mFirstTestActivity;

    public ApplicationTest() {
        super(MainActivity.class);
    }

    public ApplicationTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mFirstTestActivity = getActivity();
    }


    public void testPreconditions() {
        assertNotNull("mFirstTestActivity is null", mFirstTestActivity);
    }
}