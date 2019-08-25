package com.lee.owner.algorithm;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lee.owner.algorithm.impl.SelectSort;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 排序对象工厂
 * @Date 2019-08-25 09:35
 * @Author joseph.li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sorts {

    /**
     * 通过枚举生成单例
     */
    @Getter
    private enum SortHolder {

        SELECT_SORT("select", new SelectSort()),
        ;

        private final String sortName;

        private final Sort sort;

        SortHolder(String sortName, Sort sort) {
            this.sortName = sortName;
            this.sort = sort;
        }
    }

    /**
     * 获取排序对象
     * @param sortName 排序的类型
     * @return 排序对象
     */
    public static Sort getSort(String sortName) {
        if (Strings.isNullOrEmpty(sortName)) {
            return null;
        }
        for (SortHolder sortHolder : SortHolder.values()) {
            if (sortName.equals(sortHolder.getSortName())) {
                return sortHolder.getSort();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Sort sort = getSort("select");
        if (sort == null) {
            return;
        }

        List<Integer> list = Lists.newArrayList(8, 3,1, 2, 9, 6);
        List<Integer> sortedList = sort.sort(list);
    }
}
