package io.tan.datasource.dynamic.aop;

import io.tan.datasource.dynamic.annotation.TargetDataSource;
import io.tan.datasource.dynamic.config.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 感谢： https://blog.csdn.net/xp541130126/article/details/81739760
 * @ClassName: DynamicDataSourceAspect
 * @Description: TODO(利用AOP切面实现数据源的动态切换)
 * @author wev
 * @date 2019年7月8日 下午6:04:37
 *
 */
@Aspect
/** 保证该AOP在@Transactional之前执行
 * @author 13320*/
@Order(	1)
@Component
@Slf4j
public class DynamicDataSourceAspect{

	@Before("@annotation(ds)")
	public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
		String dsId = ds.dbname();
		if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
			log.debug("Use DataSource :{} >", dsId, point.getSignature());
			DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
		} else {
			log.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
		}
	}

	@After("@annotation(ds)")
	public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
		log.debug("Revert DataSource : " + ds.dbname() + " > " + point.getSignature());
		DynamicDataSourceContextHolder.removeDataSourceRouterKey();

	}

}
