package com.xuhj.kotlin.model;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/8/24
 */
public class JvUser implements  Cloneable{

    public String name;

    public int age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
