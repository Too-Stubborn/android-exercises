package com.xuhj.testing.suite;

import com.xuhj.testing.junit.JunitListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({JunitListActivity.class})
public class AndroidUnitTestSuite {
    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }

}
