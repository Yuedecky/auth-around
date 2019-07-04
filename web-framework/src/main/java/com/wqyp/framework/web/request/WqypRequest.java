package com.wqyp.framework.web.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WqypRequest<T> implements Serializable {


    private static final long serialVersionUID = 55634239373843L;
    @Valid
    private T data;


}
