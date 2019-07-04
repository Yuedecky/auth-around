package com.wqyp.framework.web.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResponseDto<T> {
    private List<T> list;

    @ApiModelProperty("总条数")
    private int total;

    @ApiModelProperty("当前页面")
    private int current;

    @ApiModelProperty("当前页面大小")
    private int size;
}
