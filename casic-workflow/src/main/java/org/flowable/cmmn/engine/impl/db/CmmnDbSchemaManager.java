//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.flowable.cmmn.engine.impl.db;

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
import org.flowable.cmmn.engine.CmmnEngineConfiguration;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.db.SchemaManager;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmmnDbSchemaManager implements SchemaManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmmnDbSchemaManager.class);
    public static final String LIQUIBASE_CHANGELOG = "org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml";

    public CmmnDbSchemaManager() {
    }

    public void initSchema() {
        this.initSchema(CommandContextUtil.getCmmnEngineConfiguration());
    }

    public void initSchema(CmmnEngineConfiguration cmmnEngineConfiguration) {
        this.initSchema(cmmnEngineConfiguration, cmmnEngineConfiguration.getDatabaseSchemaUpdate());
    }

    public void initSchema(CmmnEngineConfiguration cmmnEngineConfiguration, String databaseSchemaUpdate) {
        Object liquibase = null;

        try {
            if ("create-drop".equals(databaseSchemaUpdate)) {
                this.schemaCreate();
            } else if ("drop-create".equals(databaseSchemaUpdate)) {
                this.schemaDrop();
                this.schemaCreate();
            } else if ("true".equals(databaseSchemaUpdate)) {
                String databaseType = cmmnEngineConfiguration.getDatabaseType();
                System.out.println("current databaseType is : "+databaseType);
                if(!databaseType.equals(AbstractEngineConfiguration.DATABASE_TYPE_OSCAR)){
                    this.schemaUpdate();
                }
            } else if ("false".equals(databaseSchemaUpdate)) {
                this.schemaCheckVersion();
            }
        } catch (Exception var8) {
            throw new FlowableException("Error initialising cmmn data model", var8);
        } finally {
            this.closeDatabase((Liquibase)liquibase);
        }

    }

    protected Liquibase createLiquibaseInstance(CmmnEngineConfiguration cmmnEngineConfiguration) throws SQLException, DatabaseException, LiquibaseException {
        Connection jdbcConnection = null;
        CommandContext commandContext = CommandContextUtil.getCommandContext();
        if (commandContext == null) {
            jdbcConnection = cmmnEngineConfiguration.getDataSource().getConnection();
        } else {
            jdbcConnection = CommandContextUtil.getDbSqlSession(commandContext).getSqlSession().getConnection();
        }

        if (!jdbcConnection.getAutoCommit()) {
            jdbcConnection.commit();
        }

        DatabaseConnection connection = new JdbcConnection(jdbcConnection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        database.setDatabaseChangeLogTableName("ACT_CMMN_" + database.getDatabaseChangeLogTableName());
        database.setDatabaseChangeLogLockTableName("ACT_CMMN_" + database.getDatabaseChangeLogLockTableName());
        String databaseSchema = cmmnEngineConfiguration.getDatabaseSchema();
        if (StringUtils.isNotEmpty(databaseSchema)) {
            database.setDefaultSchemaName(databaseSchema);
            database.setLiquibaseSchemaName(databaseSchema);
        }

        String databaseCatalog = cmmnEngineConfiguration.getDatabaseCatalog();
        if (StringUtils.isNotEmpty(databaseCatalog)) {
            database.setDefaultCatalogName(databaseCatalog);
            database.setLiquibaseCatalogName(databaseCatalog);
        }

        return this.createLiquibaseInstance(database);
    }

    public Liquibase createLiquibaseInstance(Database database) throws LiquibaseException {
        return new Liquibase("org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
    }

    public void schemaCreate() {
        Liquibase liquibase = null;

        try {
            this.getCommonSchemaManager().schemaCreate();
            this.getIdentityLinkSchemaManager().schemaCreate();
            this.getTaskSchemaManager().schemaCreate();
            this.getVariableSchemaManager().schemaCreate();
            this.getJobSchemaManager().schemaCreate();
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getCmmnEngineConfiguration());
            liquibase.update("cmmn");
        } catch (Exception var6) {
            throw new FlowableException("Error creating CMMN engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    public void schemaDrop() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getCmmnEngineConfiguration());
            liquibase.dropAll();
        } catch (Exception var16) {
            LOGGER.info("Error dropping CMMN engine tables", var16);
        } finally {
            this.closeDatabase(liquibase);
        }

        try {
            this.getJobSchemaManager().schemaDrop();
        } catch (Exception var15) {
            LOGGER.info("Error dropping job tables", var15);
        }

        try {
            this.getVariableSchemaManager().schemaDrop();
        } catch (Exception var14) {
            LOGGER.info("Error dropping variable tables", var14);
        }

        try {
            this.getTaskSchemaManager().schemaDrop();
        } catch (Exception var13) {
            LOGGER.info("Error dropping task tables", var13);
        }

        try {
            this.getIdentityLinkSchemaManager().schemaDrop();
        } catch (Exception var12) {
            LOGGER.info("Error dropping identity link tables", var12);
        }

        try {
            this.getCommonSchemaManager().schemaDrop();
        } catch (Exception var11) {
            LOGGER.info("Error dropping common tables", var11);
        }

    }

    public String schemaUpdate() {
        Liquibase liquibase = null;

        try {
            this.getCommonSchemaManager().schemaUpdate();
            if (CommandContextUtil.getCmmnEngineConfiguration().isExecuteServiceSchemaManagers()) {
                this.getIdentityLinkSchemaManager().schemaUpdate();
                this.getTaskSchemaManager().schemaUpdate();
                this.getVariableSchemaManager().schemaUpdate();
                this.getJobSchemaManager().schemaUpdate();
            }

            liquibase = this.createLiquibaseInstance(CommandContextUtil.getCmmnEngineConfiguration());
            liquibase.update("cmmn");
        } catch (Exception var6) {
            throw new FlowableException("Error updating CMMN engine tables", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

        return null;
    }

    public void schemaCheckVersion() {
        Liquibase liquibase = null;

        try {
            liquibase = this.createLiquibaseInstance(CommandContextUtil.getCmmnEngineConfiguration());
            liquibase.validate();
        } catch (Exception var6) {
            throw new FlowableException("Error validating app engine schema", var6);
        } finally {
            this.closeDatabase(liquibase);
        }

    }

    protected SchemaManager getCommonSchemaManager() {
        return CommandContextUtil.getCmmnEngineConfiguration().getCommonSchemaManager();
    }

    protected SchemaManager getIdentityLinkSchemaManager() {
        return CommandContextUtil.getCmmnEngineConfiguration().getIdentityLinkSchemaManager();
    }

    protected SchemaManager getVariableSchemaManager() {
        return CommandContextUtil.getCmmnEngineConfiguration().getVariableSchemaManager();
    }

    protected SchemaManager getTaskSchemaManager() {
        return CommandContextUtil.getCmmnEngineConfiguration().getTaskSchemaManager();
    }

    protected SchemaManager getJobSchemaManager() {
        return CommandContextUtil.getCmmnEngineConfiguration().getJobSchemaManager();
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
