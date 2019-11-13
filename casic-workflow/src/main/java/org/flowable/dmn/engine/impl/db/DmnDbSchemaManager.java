//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.dmn.engine.impl.db;

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
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.db.SchemaManager;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.dmn.engine.DmnEngineConfiguration;
import org.flowable.dmn.engine.impl.util.CommandContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DmnDbSchemaManager implements SchemaManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DmnDbSchemaManager.class);
    public static String LIQUIBASE_CHANGELOG = "org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml";

    public DmnDbSchemaManager() {

    }

    public void initSchema() {
        this.initSchema(CommandContextUtil.getDmnEngineConfiguration());
    }

    public void initSchema(DmnEngineConfiguration dmnEngineConfiguration) {
        this.initSchema(dmnEngineConfiguration, dmnEngineConfiguration.getDatabaseSchemaUpdate());
    }

    public void initSchema(DmnEngineConfiguration dmnEngineConfiguration, String databaseSchemaUpdate) {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(dmnEngineConfiguration);
            if ("drop-create".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Dropping and creating schema DMN");
                liquibase.dropAll();
                liquibase.update("dmn");
            } else if ("true".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Updating schema DMN");
                String databaseType = dmnEngineConfiguration.getDatabaseType();
                if( !databaseType.equals(AbstractEngineConfiguration.DATABASE_TYPE_OSCAR)){
                    liquibase.update("dmn");
                }
            } else if ("false".equals(databaseSchemaUpdate)) {
                LOGGER.debug("Validating schema DMN");
                liquibase.validate();
            }
        } catch (Exception var8) {
            throw new FlowableException("Error initialising dmn data model", var8);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public Liquibase createLiquibaseInstance(DmnEngineConfiguration dmnEngineConfiguration) throws SQLException, DatabaseException, LiquibaseException {
        Connection jdbcConnection = null;
        CommandContext commandContext = CommandContextUtil.getCommandContext();
        if (commandContext == null) {
            jdbcConnection = dmnEngineConfiguration.getDataSource().getConnection();
        } else {
            jdbcConnection = CommandContextUtil.getDbSqlSession(commandContext).getSqlSession().getConnection();
        }

        if (!jdbcConnection.getAutoCommit()) {
            jdbcConnection.commit();
        }

        DatabaseConnection connection = new JdbcConnection(jdbcConnection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        database.setDatabaseChangeLogTableName("ACT_DMN_" + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName("ACT_DMN_" + database.getDatabaseChangeLogLockTableName());
        String databaseSchema = dmnEngineConfiguration.getDatabaseSchema();
        if (StringUtils.isNotEmpty(databaseSchema)) {
            database.setDefaultSchemaName(databaseSchema);
            database.setLiquibaseSchemaName(databaseSchema);
        }

        String databaseCatalog = dmnEngineConfiguration.getDatabaseCatalog();
        if (StringUtils.isNotEmpty(databaseCatalog)) {
            database.setDefaultCatalogName(databaseCatalog);
            database.setLiquibaseCatalogName(databaseCatalog);
        }

        Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG, new ClassLoaderResourceAccessor(), database);
        return liquibase;
    }

    public void schemaCreate() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getDmnEngineConfiguration());
            liquibase.update("dmn");
        } catch (Exception var6) {
            throw new FlowableException("Error creating DMN engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public void schemaDrop() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getDmnEngineConfiguration());
            liquibase.dropAll();
        } catch (Exception var6) {
            throw new FlowableException("Error dropping DMN engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public String schemaUpdate() {
        this.schemaCreate();
        return null;
    }

    public void schemaCheckVersion() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getDmnEngineConfiguration());
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
