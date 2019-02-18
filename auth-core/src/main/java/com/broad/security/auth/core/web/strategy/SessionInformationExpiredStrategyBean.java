package com.broad.security.auth.core.web.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

@Slf4j
public class SessionInformationExpiredStrategyBean implements SessionInformationExpiredStrategy {


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.error("session information expired:{}", event.getSessionInformation());
    }
}
