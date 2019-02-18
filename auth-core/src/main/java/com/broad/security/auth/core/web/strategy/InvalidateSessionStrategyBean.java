package com.broad.security.auth.core.web.strategy;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InvalidateSessionStrategyBean extends AbstractSessionStrategy {

    public InvalidateSessionStrategyBean(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        onSessionInvalidate(request,response);
    }
}
