package io.tan.datasource.dynamic.aop;

import io.tan.datasource.dynamic.annotation.TargetDataSource;
import io.tan.datasource.dynamic.config.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * @author tn
 * @version 1
 * @ClassName DynamicDataSourceAnnotationInterceptor
 * @description  aop
 * @date 2020/6/27 0:46
 */
@Slf4j
public class DynamicDataSourceAnnotationInterceptor  implements MethodInterceptor {



    /**
     * 缓存方法注解值
     */
    private static final Map<Method, String> METHOD_CACHE = new HashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String datasource = determineDatasource(invocation);
            if (! DynamicDataSourceContextHolder.containsDataSource(datasource)) {
                log.info("数据源[{}]不存在，使用默认数据源 >", datasource);
            }
            DynamicDataSourceContextHolder.setDataSourceRouterKey(datasource);
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeDataSourceRouterKey();
        }
    }

    private String determineDatasource(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (METHOD_CACHE.containsKey(method)) {
            return METHOD_CACHE.get(method);
        } else {
            TargetDataSource ds = method.isAnnotationPresent(TargetDataSource.class) ? method.getAnnotation(TargetDataSource.class)
                    : AnnotationUtils.findAnnotation(method.getDeclaringClass(), TargetDataSource.class);
            METHOD_CACHE.put(method, ds.dbname());
            return ds.dbname();
        }
    }
}
