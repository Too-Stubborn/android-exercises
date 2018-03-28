package com.xuhj.kotlin.ui;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xuhj.kotlin.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = "123";
        List<String> ls = Collections.checkedList(new ArrayList<String>(), String.class);
        Set<Integer> si = Collections.singleton(23);
        Set<Integer> si2 = Collections.emptySet();
        int i = 0;

        ApplicationInfo ai = new ApplicationInfo();
        Collections.emptyList();

        new ArrayList<String>().iterator();
        new HashSet<String>().iterator();
    }

    interface Book {
    }

    class BookA implements Book {

    }

    class BookB implements Book {

    }

    public <T extends Book> T create(Class<T> clz) {
        Book book = null;
        try {
            book = (Book) Class.forName(clz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) book;
    }


}
