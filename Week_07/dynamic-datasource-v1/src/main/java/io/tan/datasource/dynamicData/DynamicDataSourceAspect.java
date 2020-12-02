package io.tan.datasource.dynamicData;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 
 * @ClassName: DynamicDataSourceAspect 
 * @Description: TODO(利用AOP切面实现数据源的动态切换) 
 * @author tn 
 * @date 2019年7月8日 下午6:04:37 
 *
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
@Slf4j
public class DynamicDataSourceAspect {

	//	 private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);


	@Before("@annotation(ds)")
	public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
		String dsId = ds.name();
		if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
			log.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
		} else {
			log.debug("Use DataSource : {} > {}", ds.name(), point.getSignature());
			System.out.println("Use DataSource :"+ds.name() +" >"+ point.getSignature());
			DynamicDataSourceContextHolder.setDataSourceType(ds.name());
		}
	}

	@After("@annotation(ds)")
	public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
		log.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
		System.out.println("Revert DataSource :"+ds.name() +" >"+ point.getSignature());
		DynamicDataSourceContextHolder.clearDataSourceType();
	}



}
