//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.content.engine.impl.db;

import java.sql.Connection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.db.SchemaManager;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.util.CommandContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentDbSchemaManager implements SchemaManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentDbSchemaManager.class);
    public static String LIQUIBASE_CHANGELOG = "org/flowable/content/db/liquibase/flowable-content-db-changelog.xml";

    public ContentDbSchemaManager() {
    }

    public void schemaCreate() {
        Liquibase liquibase = createLiquibaseInstance(CommandContextUtil.getContentEngineConfiguration());

        try {
            liquibase.update("content");
        } catch (Exception var6) {
            throw new FlowableException("Error creating content engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public void schemaDrop() {
        Liquibase liquibase = createLiquibaseInstance(CommandContextUtil.getContentEngineConfiguration());

        try {
            liquibase.dropAll();
        } catch (Exception var6) {
            throw new FlowableException("Error dropping content engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public String schemaUpdate() {
        this.schemaCreate();
        return null;
    }

    protected static Liquibase createLiquibaseInstance(ContentEngineConfiguration configuration) {
        try {
            Connection jdbcConnection = null;
            CommandContext commandContext = CommandContextUtil.getCommandContext();
            if (commandContext == null) {
                jdbcConnection = configuration.getDataSource().getConnection();
            } else {
                jdbcConnection = CommandContextUtil.getDbSqlSession(commandContext).getSqlSession().getConnection();
            }

            if (!jdbcConnection.getAutoCommit()) {
                jdbcConnection.commit();
            }

            DatabaseConnection connection = new JdbcConnection(jdbcConnection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            database.setDatabaseChangeLogTableName("ACT_CO_" + database.getDatabaseChangeLogTableName());
            database.setDatabaseChangeLogLockTableName("ACT_CO_" + database.getDatabaseChangeLogLockTableName());
            Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG, new ClassLoaderResourceAccessor(), database);
            return liquibase;
        } catch (Exception var6) {
            throw new FlowableException("Error creating liquibase instance", var6);
        }
    }

    public void initSchema(ContentEngineConfiguration configuration, String databaseSchemaUpdate) {
        Liquibase liquibase = null;
        try {
            String databaseType = configuration.getDatabaseType();
            liquibase = createLiquibaseInstance(configuration);
            if ("drop-create".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Dropping and creating schema Content");
                liquibase.dropAll();
                liquibase.update("content");
            } else if ("true".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Updating schema Content");
                if( !databaseType.equals(AbstractEngineConfiguration.DATABASE_TYPE_OSCAR)){
                    liquibase.update("content");
                }
            } else if ("false".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Validating schema Content");
                liquibase.validate();
            }
        } catch (Exception var8) {
            throw new FlowableException("Error initialising Content schema", var8);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public void schemaCheckVersion() {
        Liquibase liquibase = null;

        try {
            liquibase = createLiquibaseInstance(CommandContextUtil.getContentEngineConfiguration());
            liquibase.validate();
        } catch (Exception var6) {
            throw new FlowableException("Error validating app engine schema", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

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
