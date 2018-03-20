package com.xuhj.testing.junit;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Describe :  描述
 * Author   :  xuhj
 * Date     :  2016/11/3
 * Version  :  1.0
 */
//@RunWith(Parameterized.class)
@RunWith(MockitoJUnitRunner.class)
public class LoginActivityTest {

    JunitLoginActivity mActivity;

//    @Parameterized.Parameter
//    public String emails;

    @Rule
    public TemporaryFolder temporaryFolder;

    @Before
    public void setUp() throws Exception {
        mActivity = new JunitLoginActivity();
    }

    @After
    public void tearDown() throws Exception {

    }

//    @Parameterized.Parameters
//    public static Collection<String> init() {
//        String[] array = {null, "123", "123@qq.com"};
//        return Arrays.asList(array);
//    }

    @Test
    public void mockTest() throws Exception {
        List list = Mockito.spy(new LinkedList());
        when(list.size()).thenReturn(100);
        list.add("test1");
        list.add("test2");
        list.add("test3");
        when(list.get(anyInt())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return String.valueOf(new Random().nextInt(100));
            }
        });
        when(list.get(0)).thenReturn("0");
        System.out.println(list.get(0) + "---" + list.get(1) + "---" + list.get(2));

        Mockito.verify(list).get(0);
    }

    @Test
    public void isEmailValid() throws Exception {
//        try {
//            Assert.assertThat(mActivity.isEmailValid("123"), is(true));
//        } catch (Exception e) {
//            Assert.fail("" + e.getMessage());
//        }
        Assert.assertTrue(mActivity.isEmailValid("123"));
        Assert.assertTrue(mActivity.isEmailValid("123@qq.com"));
    }

    @Test
    public void isPasswordValid() throws Exception {
        Assert.assertTrue(mActivity.isPasswordValid("123"));
        Assert.assertTrue(mActivity.isPasswordValid("1234"));
    }

}