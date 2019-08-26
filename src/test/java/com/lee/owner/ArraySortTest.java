package com.lee.owner;

import com.lee.owner.algorithm.ArraySort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Random;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArraySortTest {

    /**
     * selectArraySort
     * bubbleArraySort
     * insertionArraySort
     */
    @Autowired
    @Qualifier("insertionArraySort")
    private ArraySort sort;

    @Test
    public void testArraySort() {
        int size = 10000;
        int max = 10000;

        Integer[] arr = new Integer[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = (random.nextInt(max));
        }

        Stream.of(arr).forEach(this::myPrint);

        long start = System.currentTimeMillis();

        Integer[] sortedArr = sort.sort(arr);

        System.out.println("\n花费时间：" + (System.currentTimeMillis() - start));

        Stream.of(sortedArr).forEach(this::myPrint);
    }

    private void myPrint(Object object) {
        System.out.print(object.toString() + " ");
    }

}
