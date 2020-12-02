package io.tan.datasource.dynamic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**感谢： https://blog.csdn.net/xp541130126/article/details/81739760
 *
 * @author WEB
 * @ClassName: DynamicDataSource
 * @Description: TODO(动态数据源切换) 动态数据源路由
 * @date 2019年7月8日 下午6:04:21
 *
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource  {

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
		log.info("当前数据源是：{}", dataSourceName==null?"master":dataSourceName);
		return DynamicDataSourceContextHolder.getDataSourceRouterKey();
	}
}
