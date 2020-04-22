package com.lee.owner.repository.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @author joseph.li
 * @date 2020/4/22 6:06 下午
 */
@Data
@TableName(value = "t_produce_task")
public class ProduceTask {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日期 20200101，也可以时间戳
     */
    @TableField(value = "date")
    private Long date;

    /**
     * 任务类型，1-逾期
     */
    @TableField(value = "type")
    private Short type;

    /**
     * 分片
     */
    @TableField(value = "`separator`")
    private Short separator;

    /**
     * 分片详情
     */
    @TableField(value = "separator_detail")
    private String separatorDetail;

    /**
     * 状态，0-初始态，1-生产数据中，2-生产固定条数数据成功，3-生产所有数据完毕，4-生产数据失败
     */
    @TableField(value = "produce_status")
    private Byte produceStatus;

    /**
     * 生产数据详情 成功/失败原因
     */
    @TableField(value = "produce_deatail")
    private String produceDeatail;

    /**
     * 生产者当前生产到的id
     */
    @TableField(value = "current_produce_id")
    private Long currentProduceId;

    /**
     * 创建时间 默认系统当前时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间 默认系统当前时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * cas版本号
     */
    @TableField(value = "cas_version")
    private Long casVersion;

    /**
     * 扩展信息
     */
    @TableField(value = "extend_info")
    private String extendInfo;
}