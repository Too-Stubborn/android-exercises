package com.xuhj.jellybean;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 */
public class SkuTest {

    @Test
    public void main() throws Exception {

        String[] a = new String[]{"1", "2"};
        String[] b = new String[]{"a", "b", "c"};
        String[] c = new String[]{"A", "B", "C", "D"};
        String[] d = new String[]{"x", "y", "z"};

        List<String[]> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        int column = list.size();
        int row = 1;
        int n = 1;

        // caculate size
        for (int i = 0; i < list.size(); i++) {
            String[] t = list.get(i);
            row = row * t.length;
        }

        // --------------------------------------------------------------
        List<String[]> newList = new ArrayList<>();

        // column
        for (int i = 0; i < column; i++) {
            // row
            String[] curTemp = null;
            String[] preTemp = null;

            curTemp = list.get(i);
            if (i > 0) {
                preTemp = list.get(i - 1);
            }

            if (i == 0) {
                // except first property
                n = 1;
            } else {
                // after property
                n = n * preTemp.length;
            }

            // put array[]
            int index = 0;
            for (int k = 0; k < row / n; k++) {
                for (int l = 0; l < n; l++) {
                    String[] newArray = null;
                    if (index >= newList.size()) {
                        newList.add(new String[column]);
                    }
                    newArray = newList.get(index);
                    newArray[i] = curTemp[k % curTemp.length];
                    newList.set(index, newArray);
                    index++;
                }
            }

        }
        // --------------------------------------------------------------

        // print
        for (int i = 0; i < newList.size(); i++) {
            String[] outs = newList.get(i);
            for (int j = 0; j < outs.length; j++) {
                System.out.print("   " + outs[j]);
            }
            System.out.println("");
        }

    }
}
