package com.broad.security.auth.core.social.qq.connect;

import com.broad.security.auth.core.social.qq.api.IQQ;
import com.broad.security.auth.core.social.qq.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQAdapter implements ApiAdapter<IQQ> {

    @Override
    public boolean test(IQQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(IQQ api, ConnectionValues values)  {
        QQUserInfo userInfo = api.getQQUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(IQQ api) {
        return null;
    }

    @Override
    public void updateStatus(IQQ api, String message) {

    }
}
