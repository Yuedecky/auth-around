package com.wqyp.framework.web.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePojo implements Serializable {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String createId;

    private String updateId;
}
