package com.lee.owner.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author joseph.li
 * @date 2019-10-08 19:32
 */
@Data
@Accessors(chain = true)
public class DemoDTO {

    private String name;

    private Integer age;

    private Byte sex;
}
