package com.motech.test.liquibase;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandLineParsingException;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.Main;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TestLiquibase {
    public static void main(String[] args) throws SQLException, IOException, CommandLineParsingException {
        update();

    }

    public static void update2() throws SQLException, IOException, CommandLineParsingException {
        Main.main(new String[]{"--driver=org.h2.Driver",
                "--classpath=/Users/srikanthnutigattu/.m2/repository/com/h2database/h2/1.3.172/h2-1.3.172.jar",
                "--changeLogFile=/Users/srikanthnutigattu/IdeaProjects/Standalone-Hibernate-JPA/src/main/java/com/motech/test/liquibase/changelog.xml",
                "--url=jdbc:h2:~/motechtest2",
                "--username=sa",
                "update"});
    }

    public static void update() throws SQLException {
        Connection connection = null;
        try {
//            DataSource dataSource = (DataSource) ic.lookup(this.dataSourceName);

//            JDBCDataSource dataSource = new JDBCDataSource();
//            dataSource.setUrl("jdbc:hsqldb:file:~/motechtest");
//            dataSource.setUser("sa");


            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:~/motechtest3");
            dataSource.setUser("sa");

            connection = dataSource.getConnection();

            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

            ResourceAccessor clFO = new ClassLoaderResourceAccessor();
            ResourceAccessor fsFO = new FileSystemResourceAccessor();


            liquibase.database.Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("/Users/srikanthnutigattu/IdeaProjects/Standalone-Hibernate-JPA/src/main/java/com/motech/test/liquibase/changelog2.xml", new CompositeResourceAccessor(clFO, fsFO, threadClFO), database);

            liquibase.update("dev");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DatabaseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (LiquibaseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
