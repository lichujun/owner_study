package com.lee.owner.asyn;

/**
 * @author joseph.li
 * @date 2020/6/23 5:42 下午
 */
public interface MessageProcessAssistant {

    boolean canProcess();

    void processWait();

    void initProcessCount();

}
