package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName consume
 */
@TableName(value ="consume")
@Data
public class Consume implements Serializable {
    /**
     * 消费金额
     */
    private BigDecimal consumption;

    /**
     * 当前额度，此时还剩多少钱
     */
    private BigDecimal deposit;
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 消费时间
     */
    private LocalDateTime tTime;

    /**
     * 1代表消费，0代表退款
     */
    private Short sign;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}