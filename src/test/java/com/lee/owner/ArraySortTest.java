package com.lee.owner;

import com.lee.owner.algorithm.ArraySort;
import com.lee.owner.algorithm.ArraySorts;
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
     * selectionArraySort       选择排序
     * bubbleArraySort          冒泡排序
     * insertionArraySort       插入排序
     * shellArraySort           希尔排序
     * mergeArraySort           归并排序
     * quickArraySort           快速排序
     * heapArraySort            堆排序
     */
    @Autowired
    @Qualifier("heapArraySort")
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

        System.out.println();
    }

    @Test
    public void testRound() {
        int size = 50000;
        int round = 100;

        Integer[] arr = new Integer[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < round; i++) {
            ArraySorts.swap(arr, random.nextInt(size), random.nextInt(size));
        }

        Stream.of(arr).forEach(this::myPrint);

        long start = System.currentTimeMillis();

        Integer[] sortedArr = sort.sort(arr);

        System.out.println("\n花费时间：" + (System.currentTimeMillis() - start));

        Stream.of(sortedArr).forEach(this::myPrint);

        System.out.println();

    }

    private void myPrint(Object object) {
        System.out.print(object.toString() + " ");
    }

}
