package com.broad.security.auth.core.validate;

import com.broad.security.auth.core.properties.constants.AuthConstants;

public enum ValidateCodeType {

    SMS {
        @Override
        public String getParamNameOnValidate() {
            return AuthConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return AuthConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };


    public abstract String getParamNameOnValidate();
}
