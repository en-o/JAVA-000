package io.tan.datasource;

import io.tan.datasource.dynamic.register.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
/*把用到的资源导入到当前容器中*/
@Import({DynamicDataSourceRegister.class})
public class DynamicDatasourceV2Application {

	public static void main(String[] args) {
		SpringApplication.run(DynamicDatasourceV2Application.class, args);
	}

}
