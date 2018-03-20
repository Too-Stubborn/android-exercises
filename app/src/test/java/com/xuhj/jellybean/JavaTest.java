package com.xuhj.jellybean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe :  JAVA 测试类
 * Author   :  xuhj
 * Date     :  2016/11/23
 * Version  :  1.0
 */
public class JavaTest {

    @Test
    public void test() throws Exception {
        List<String> list = new ArrayList<>(3);
        for (int i = 5; i >= 0; i--) {
            list.add("a" + i);
        }
        System.out.println(list.toString());
    }
}
