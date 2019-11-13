package org.casic.workflow.config;

import org.casic.workflow.config.properties.DataSourceConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.datasource")
@PropertySource(value="classpath:config/jdbc-dev.properties",encoding="UTF-8",ignoreResourceNotFound=false)
public class DataSourceConfig extends DataSourceConfigProperty {
	@Override
	public String toString() {
		return super.toString();
	}
}
