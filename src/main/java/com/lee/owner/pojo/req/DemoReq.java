package com.lee.owner.pojo.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author joseph.li
 * @date 2019-10-08 19:32
 */
@Data
@Accessors(chain = true)
public class DemoReq {

    private String name;
}
