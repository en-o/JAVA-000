package io.tan.datasource.dynamicData;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
* @ClassName: DynamicDataSource 
* @Description: TODO(动态数据源切换) 
* @author tn 
* @date 2019年7月8日 下午6:04:21 
*
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	 protected Object determineCurrentLookupKey() {
		 return DynamicDataSourceContextHolder.getDataSourceType();
    }

}
