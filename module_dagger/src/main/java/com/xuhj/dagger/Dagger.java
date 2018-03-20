package com.xuhj.dagger;

/**
 * 描述
 *
 * @author xuhj
 */
public class Dagger {
    private static Dagger ourInstance = new Dagger();

    public static Dagger getInstance() {
        return ourInstance;
    }

    private Dagger() {
    }
}
