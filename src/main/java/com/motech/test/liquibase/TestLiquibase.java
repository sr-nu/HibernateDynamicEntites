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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

public class TestLiquibase {
    public static void main(String[] args) throws SQLException, IOException, CommandLineParsingException {
        updateFromJsonString();

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
            Liquibase liquibase = new Liquibase("com/motech/test/liquibase/changelog.xml", new CompositeResourceAccessor(clFO, fsFO, threadClFO), database);

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




    public static void updateFromXmlString() throws SQLException {
        Connection connection = null;
        try {

            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:~/motechtest3");
            dataSource.setUser("sa");

            connection = dataSource.getConnection();

            liquibase.database.Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("change_set_1.xml", new ResourceAccessor() {
                @Override
                public InputStream getResourceAsStream(String file) throws IOException {
                    String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<databaseChangeLog\n" +
                            "        xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n" +
                            "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                            "        xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog\n" +
                            "         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd\">\n" +
                            "        <changeSet id=\"1\" author=\"bob\">\n" +
                            "            <createTable tableName=\"department\">\n" +
                            "                <column name=\"id\" type=\"int\">\n" +
                            "                    <constraints primaryKey=\"true\" nullable=\"false\"/>\n" +
                            "                </column>\n" +
                            "                <column name=\"name\" type=\"varchar(50)\">\n" +
                            "                    <constraints nullable=\"false\"/>\n" +
                            "                </column>\n" +
                            "                <column name=\"active\" type=\"boolean\" defaultValueBoolean=\"true\"/>\n" +
                            "            </createTable>\n" +
                            "        </changeSet>\n" +
                            "</databaseChangeLog>";



                    return new ByteArrayInputStream(xml.getBytes());



                }

                @Override
                public Enumeration<URL> getResources(String packageName) throws IOException {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public ClassLoader toClassLoader() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            }, database);



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




    public static void updateFromJsonString() throws SQLException {
        Connection connection = null;
        try {

            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:~/motechtest3");
            dataSource.setUser("sa");

            connection = dataSource.getConnection();

            liquibase.database.Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("change_set_1.json", new ResourceAccessor() {
                @Override
                public InputStream getResourceAsStream(String file) throws IOException {
                    String json = "{\n" +
                            "    \"databaseChangeLog\": [\n" +
                            "        {\n" +
                            "            \"preConditions\": [\n" +
                            "                {\n" +
                            "                    \"runningAs\": {\n" +
                            "                        \"username\": \"sa\"\n" +
                            "                    }\n" +
                            "                }\n" +
                            "            ]\n" +
                            "        },\n" +
                            "\n" +
                            "        {\n" +
                            "            \"changeSet\": {\n" +
                            "                \"id\": \"1\",\n" +
                            "                \"author\": \"nvoxland\",\n" +
                            "                \"changes\": [\n" +
                            "                    {\n" +
                            "                        \"createTable\": {\n" +
                            "                            \"tableName\": \"person\",\n" +
                            "                            \"columns\": [\n" +
                            "                                {\n" +
                            "                                    \"column\": {\n" +
                            "                                        \"name\": \"id\",\n" +
                            "                                        \"type\": \"int\",\n" +
                            "                                        \"autoIncrement\": true,\n" +
                            "                                        \"constraints\": {\n" +
                            "                                            \"primaryKey\": true,\n" +
                            "                                            \"nullable\": false\n" +
                            "                                        },\n" +
                            "                                    }\n" +
                            "                                },\n" +
                            "                                {\n" +
                            "                                    \"column\": {\n" +
                            "                                        \"name\": \"firstname\",\n" +
                            "                                        \"type\": \"varchar(50)\"\n" +
                            "                                    }\n" +
                            "                                },\n" +
                            "                                {\n" +
                            "                                    \"column\": {\n" +
                            "                                        \"name\": \"lastname\",\n" +
                            "                                        \"type\": \"varchar(50)\",\n" +
                            "                                        \"constraints\": {\n" +
                            "                                            \"nullable\": false\n" +
                            "                                        },\n" +
                            "                                    }\n" +
                            "                                },\n" +
                            "                                {\n" +
                            "                                    \"column\": {\n" +
                            "                                        \"name\": \"state\",\n" +
                            "                                        \"type\": \"char(2)\"\n" +
                            "                                    }\n" +
                            "                                }\n" +
                            "                            ]\n" +
                            "                        }\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            }\n" +
                            "        }"+
                            "    ]\n" +
                            "}";

                    return new ByteArrayInputStream(json.getBytes());



                }

                @Override
                public Enumeration<URL> getResources(String packageName) throws IOException {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public ClassLoader toClassLoader() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            }, database);



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
