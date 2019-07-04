package com.wqyp.framework.web.response;

import com.wqyp.framework.web.bean.PageResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WqypPageResponse<T> {

    private PageResponseDto<T> data;

    public static <T> WqypPageResponse<T> success(PageResponseDto<T> data) {
        WqypPageResponse<T> pageResponse = new WqypPageResponse<>();
        pageResponse.setData(data);
        return pageResponse;
    }


}
