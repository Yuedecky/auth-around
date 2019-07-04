package com.wqyp.framework.web.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequestDto<T> {
    @ApiModelProperty(value = "页码", example = "1")
    private int pageNum = 1;

    @ApiModelProperty(value = "一页数据大小", example = "20")
    private int pageSize = 20;
}
