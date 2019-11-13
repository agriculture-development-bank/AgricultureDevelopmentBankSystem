package org.casic.workflow.common;

import com.casic.common.config.Global;
import com.casic.common.utils.StringUtils;
import org.flowable.common.engine.impl.EngineConfigurator;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.configurator.FormEngineConfigurator;

import java.util.ArrayList;
import java.util.List;

public class CasicFlowableEngine {

    private CasicFlowableEngine(){

    }

    private static final String clientId = Global.getConfig("security.oauth2.client.client-id");
    private static final String driverClassName = isNotEmpty(clientId) ? Global.getConfig("spring.datasource.driver-class-name") : Global.getConfig("spring.datasource.driverClassName");
    private static final String url = isNotEmpty(clientId) ? Global.getConfig("spring.datasource.url") : Global.getConfig("spring.datasource.druid.master.url");
    private static final String username = isNotEmpty(clientId) ? Global.getConfig("spring.datasource.username") : Global.getConfig("spring.datasource.druid.master.username");
    private static final String password = isNotEmpty(clientId) ? Global.getConfig("spring.datasource.password") : Global.getConfig("spring.datasource.druid.master.password");

    private static boolean isNotEmpty(String str){
        boolean ret = false;
        if(StringUtils.isNotEmpty(str) && !"null".equals(str.toLowerCase())){
            ret = true;
        }
        return ret;
    }

    private static final FormEngineConfiguration formEngineConfiguration = FormEngineConfiguration.createStandaloneInMemFormEngineConfiguration();
    static {
        formEngineConfiguration.setDatabaseSchemaUpdate(FormEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        formEngineConfiguration.setJdbcDriver(driverClassName);
        formEngineConfiguration.setJdbcUrl(url);
        formEngineConfiguration.setJdbcUsername(username);
        formEngineConfiguration.setJdbcPassword(password);
    }

    public static final FormEngine casicFormEngine = formEngineConfiguration.buildFormEngine();

    public static List<EngineConfigurator> getConfigurators(){
        List<EngineConfigurator> configurators = new ArrayList<EngineConfigurator>();
        FormEngineConfigurator formEngineConfigurator = new FormEngineConfigurator();
        formEngineConfigurator.setFormEngineConfiguration(formEngineConfiguration);
        configurators.add(formEngineConfigurator);
        return configurators;
    }

    private static final ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
    static {
        processEngineConfiguration
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcDriver(driverClassName)
                .setJdbcUrl(url)
                .setJdbcUsername(username)
                .setJdbcPassword(password)
                .setAsyncExecutorActivate(false)
                .setDatabaseType("oscar")
                .setConfigurators(getConfigurators());
    }
    public static final ProcessEngine casicProcessEngine = processEngineConfiguration
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                    .setJdbcDriver(driverClassName)
                    .setJdbcUrl(url)
                    .setJdbcUsername(username)
                    .setJdbcPassword(password)
                    .setAsyncExecutorActivate(false)
                    .setDatabaseType("oscar")
                    .buildProcessEngine();
}
