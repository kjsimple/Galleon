package com.gydoc.galleon.mybatis;

import com.gydoc.galleon.GalleonException;
import com.gydoc.galleon.Constant;
import com.gydoc.galleon.IdGenerator;
import com.gydoc.galleon.SpringUtil;
import com.gydoc.galleon.domain.BizObject;
import com.gydoc.galleon.domain.NoOptimisticLocked;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Properties;

/**
 *
 */
@Intercepts({@Signature(type=Executor.class, method="update", args={MappedStatement.class, Object.class})})
public class GalleonInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        BizObject bizObject = invocation.getArgs()[1] instanceof BizObject ? (BizObject) invocation.getArgs()[1] : null;
        
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            if (bizObject != null) {
                if (bizObject.getId() == null) {
                    IdGenerator idGenerator = (IdGenerator) SpringUtil.getBean(IdGenerator.S_ID);
                    bizObject.setId(idGenerator.nextId());
                }
                bizObject.setCreationDate(Calendar.getInstance());
                bizObject.setVersion(0L);
            }
        } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            if (bizObject != null) {
                Annotation noOptimisticLocked = bizObject.getClass().getMethod("getVersion").getAnnotation(NoOptimisticLocked.class);
                Object result = invocation.proceed();
                if (noOptimisticLocked == null) {
                    Integer updated = (Integer) result;
                    if (updated != 1) {
                        throw new GalleonException(Constant.ERROR_COMMON_DOMAIN_OBJECT_STALE);
                    }
                }
                return result;
            }
        }
        return invocation.proceed();
    }

    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    public void setProperties(Properties properties) {
        
    }

}
