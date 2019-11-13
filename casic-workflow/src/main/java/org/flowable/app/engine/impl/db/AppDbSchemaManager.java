//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.app.engine.impl.db;

import java.sql.Connection;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.lang3.StringUtils;
import org.flowable.app.engine.AppEngineConfiguration;
import org.flowable.app.engine.impl.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.db.SchemaManager;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppDbSchemaManager implements SchemaManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDbSchemaManager.class);
    public static final String LIQUIBASE_CHANGELOG = "org/flowable/app/db/liquibase/flowable-app-db-changelog.xml";

    public AppDbSchemaManager() {
    }

    public void initSchema() {
        this.initSchema(CommandContextUtil.getAppEngineConfiguration());
    }

    public void initSchema(AppEngineConfiguration appEngineConfiguration) {
        this.initSchema(appEngineConfiguration, appEngineConfiguration.getDatabaseSchemaUpdate());
    }

    public void initSchema(AppEngineConfiguration appEngineConfiguration, String databaseSchemaUpdate) {
        Object liquibase = null;
        try {
            if ("create-drop".equals(databaseSchemaUpdate)) {
                this.schemaCreate();
            } else if ("drop-create".equals(databaseSchemaUpdate)) {
                this.schemaDrop();
                this.schemaCreate();
            } else if ("true".equals(databaseSchemaUpdate)) {
                String databaseType = appEngineConfiguration.getDatabaseType();
                if(!databaseType.equals(AbstractEngineConfiguration.DATABASE_TYPE_OSCAR)){
                    this.schemaUpdate();
                }else {
                    System.out.println("current databaseType is : "+databaseType);
                }
            } else if ("false".equals(databaseSchemaUpdate)) {
                this.schemaCheckVersion();
            }
        } catch (Exception var8) {
            throw new FlowableException("Error initialising app data model", var8);
        } finally {
            this.closeDatabase((Liquibase)liquibase);
        }

    }

    protected Liquibase createLiquibaseInstance(AppEngineConfiguration appEngineConfiguration) throws SQLException, DatabaseException, LiquibaseException {
        Connection jdbcConnection = null;
        CommandContext commandContext = CommandContextUtil.getCommandContext();
        if (commandContext == null) {
            jdbcConnection = appEngineConfiguration.getDataSource().getConnection();
        } else {
            jdbcConnection = CommandContextUtil.getDbSqlSession(commandContext).getSqlSession().getConnection();
        }

        if (!jdbcConnection.getAutoCommit()) {
            jdbcConnection.commit();
        }

        DatabaseConnection connection = new JdbcConnection(jdbcConnection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        database.setDatabaseChangeLogTableName("ACT_APP_" + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName("ACT_APP_" + database.getDatabaseChangeLogLockTableName());
        String databaseSchema = appEngineConfiguration.getDatabaseSchema();
        if (StringUtils.isNotEmpty(databaseSchema)) {
            database.setDefaultSchemaName(databaseSchema);
            database.setLiquibaseSchemaName(databaseSchema);
        }

        String databaseCatalog = appEngineConfiguration.getDatabaseCatalog();
        if (StringUtils.isNotEmpty(databaseCatalog)) {
            database.setDefaultCatalogName(databaseCatalog);
            database.setLiquibaseCatalogName(databaseCatalog);
        }

        return this.createLiquibaseInstance(database);
    }

    public Liquibase createLiquibaseInstance(Database database) throws LiquibaseException {
        return new Liquibase("org/flowable/app/db/liquibase/flowable-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    }

    public void schemaCreate() {
        Liquibase liquibase = null;

        try {
            this.getCommonSchemaManager().schemaCreate();
            this.getIdentityLinkSchemaManager().schemaCreate();
            this.getVariableSchemaManager().schemaCreate();
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getAppEngineConfiguration());
            liquibase.update("app");
        } catch (Exception var6) {
            throw new FlowableException("Error creating App engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public void schemaDrop() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getAppEngineConfiguration());
            liquibase.dropAll();
        } catch (Exception var12) {
            LOGGER.info("Error dropping App engine tables", var12);
        } finally {
            this.closeDatabase(liquibase);
        }

        try {
            this.getVariableSchemaManager().schemaDrop();
        } catch (Exception var11) {
            LOGGER.info("Error dropping variable tables", var11);
        }

        try {
            this.getIdentityLinkSchemaManager().schemaDrop();
        } catch (Exception var10) {
            LOGGER.info("Error dropping identity link tables", var10);
        }

        try {
            this.getCommonSchemaManager().schemaDrop();
        } catch (Exception var9) {
            LOGGER.info("Error dropping common tables", var9);
        }

    }

    public String schemaUpdate() {
        Liquibase liquibase = null;

        try {
            this.getCommonSchemaManager().schemaUpdate();
            if (CommandContextUtil.getAppEngineConfiguration().isExecuteServiceSchemaManagers()) {
                this.getIdentityLinkSchemaManager().schemaUpdate();
                this.getVariableSchemaManager().schemaUpdate();
            }
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getAppEngineConfiguration());
            liquibase.update("cmmn");
        } catch (Exception var6) {
            throw new FlowableException("Error updating App engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

        return null;
    }

    public void schemaCheckVersion() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getAppEngineConfiguration());
            liquibase.validate();
        } catch (Exception var6) {
            throw new FlowableException("Error validating app engine schema", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    protected SchemaManager getCommonSchemaManager() {
        return CommandContextUtil.getAppEngineConfiguration().getCommonSchemaManager();
    }

    protected SchemaManager getIdentityLinkSchemaManager() {
        return CommandContextUtil.getAppEngineConfiguration().getIdentityLinkSchemaManager();
    }

    protected SchemaManager getVariableSchemaManager() {
        return CommandContextUtil.getAppEngineConfiguration().getVariableSchemaManager();
    }

    private void closeDatabase(Liquibase liquibase) {
        if (liquibase != null) {
            Database database = liquibase.getDatabase();
            if (database != null && CommandContextUtil.getCommandContext() == null) {
                try {
                    database.close();
                } catch (DatabaseException var4) {
                    LOGGER.warn("Error closing database", var4);
                }
            }
        }

    }
}
