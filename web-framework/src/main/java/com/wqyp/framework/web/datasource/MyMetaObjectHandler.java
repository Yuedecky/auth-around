package com.wqyp.framework.web.datasource;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("deleted",0,metaObject);
        this.setFieldValByName("creator","",metaObject);
        this.setFieldValByName("updater","",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("updater", "", metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
