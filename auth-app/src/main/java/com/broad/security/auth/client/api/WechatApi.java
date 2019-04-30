package com.broad.security.auth.client.api;

import com.broad.security.auth.core.web.Response;
import com.broad.security.auth.core.web.request.Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class WechatApi {



    @PostMapping("/registerPhone")
    public Response<Boolean> registerPhone(Request<String> encrypedRequest){


        return Response.success(true);
    }


}
