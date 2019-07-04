package com.wqyp.framework.web.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class ResponseDto<T> {

    @ApiModelProperty("数组")
    private List<T> list;

    private static final ResponseDto INSTANCE = new ResponseDto();
    private ResponseDto(){
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseDto<T> generate(List<T> data){
        INSTANCE.setList(data);
        return INSTANCE;
    }
}
