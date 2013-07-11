package com.motech.test.customEntity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.PropertyGeneration;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.mapping.Table;

import java.sql.Types;

public class CustomEntityCreator {

//    public SessionFactory getSessionFactory() {
//
//        // physical model
//
//        Table tab = new Table();
//        tab.setName("Person");
//
//
//        Column col = new Column("id");
//        col.setSqlType("INTEGER");
//
//        tab.addColumn(col);
//
//
//        Column col2 = new Column("username");
//        col2.setSqlType("VARCHAR");
//
//        tab.addColumn(col2);
//
//
//        Configuration configuration = new Configuration();
//        Mappings mappings = configuration.createMappings();
//
//
//        SimpleValue simpleValue = new SimpleValue(mappings, tab);
//        simpleValue.addColumn(col);
//        simpleValue.setTypeName(Hibernate.INTEGER.getName());
//
//        Property property = new Property();
//        property.setName("id");
//        property.setValue(simpleValue);
//        property.setGeneration(PropertyGeneration.INSERT);
//
//        SimpleValue simpleValue2 = new SimpleValue(mappings, tab);
//        simpleValue2.addColumn(col2);
//        simpleValue2.setTypeName(Hibernate.STRING.getName());
//
//        Property property2 = new Property();
//        property2.setName("new");
//        property2.setValue(simpleValue2);
//
//
//        RootClass clazz = new RootClass();
//        clazz.setEntityPersisterClass(Person.class);
//        clazz.setTable(tab);
//        clazz.addProperty(property);
//        clazz.addProperty(property2);
//        clazz.setIdentifier(simpleValue);
//        clazz.setIdentifierProperty(property);
//
//
//
//        mappings.addClass(clazz);
////        mappings.addImport(clazz.getEntityName(), clazz.getEntityName());
//
//
//        return configuration.buildSessionFactory();
//
//    }



    public SessionFactory getSessionFactory2() {
        Configuration configuration = new Configuration().configure();
        Mappings mappings = configuration.createMappings();


        Dialect dialect = Dialect.getDialect(configuration.getProperties());

        // physical model

        Table tab  = new Table();
        tab.setName("PERSON");
        tab.setSchema("PUBLIC");


        Column id_col = new Column("id");
        id_col.setSqlType(dialect.getTypeName(Types.INTEGER));

        SimpleValue id_val = new SimpleValue(mappings, tab);
        id_val.setTypeName("int");
        id_val.addColumn(id_col);


        Property id_prop = new Property();
        id_prop.setName("id");
        id_prop.setValue(id_val);
        id_prop.setGeneration(PropertyGeneration.NEVER);


        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setName("pkey");
        primaryKey.setTable(tab);
        primaryKey.addColumn(id_col);

        tab.setPrimaryKey(primaryKey);


        RootClass clazz = new RootClass();
        clazz.setClassName(Person.class.getName());
        clazz.setProxyInterfaceName(Person.class.getName());
        clazz.setEntityName(Person.class.getName());
        clazz.setJpaEntityName(Person.class.getName());
        clazz.setTable(tab);
        clazz.setIdentifier(id_val);
        clazz.setIdentifierProperty(id_prop);



        mappings.addClass(clazz);
//        mappings.addImport(clazz.getEntityName(), clazz.getEntityName());

        Table table = mappings.addTable("PUBLIC", null, "PERSON", null, false);
        table.addColumn(id_col);
        mappings.addTableBinding("PUBLIC", null,
                "PERSON", "PERSON", null);



        return configuration.buildSessionFactory();

    }



//    protected RootClass createTableMapping(Mappings mappings,
//                                           com.manydesigns.portofino.model.database.Table aTable) {
//
//
//        Table tab = mappings.addTable("test_schema", null,
//                "test_table", null, false);
//        //tab.setName(escapeName(aTable.getTableName()));
//        //tab.setSchema(escapeName(aTable.getSchemaName()));
//        mappings.addTableBinding("test_schema", null,
//                "test_table", "test_table", null);
//
//        RootClass clazz = new RootClass();
//        clazz.setEntityName("Person");
//        clazz.setJpaEntityName("Person");
//        clazz.setClassName(new Person().getClass().getName());
//        clazz.setProxyInterfaceName(new Person().getClass().getName());
//        clazz.setLazy(true);
//        clazz.setTable(tab);
//        //clazz.setNodeName(aTable.getTableName());
//
//        List<com.manydesigns.portofino.model.database.Column> columnList =
//                new ArrayList<com.manydesigns.portofino.model.database.Column>();
//
//        for(com.manydesigns.portofino.model.database.Column modelColumn : aTable.getColumns()) {
//            int jdbcType = modelColumn.getJdbcType();
//            Class javaType = modelColumn.getActualJavaType();
//
//            //First param = null ==> doesn't really set anything, just check
//            boolean hibernateTypeOk =
//                    setHibernateType(null, modelColumn, javaType, jdbcType);
//            if (hibernateTypeOk) {
//                columnList.add(modelColumn);
//            } else {
//                logger.error("Cannot find Hibernate type for table: {}, column: {}, jdbc type: {}, type name: {}. Skipping column.",
//                        new Object[]{
//                                aTable.getQualifiedName(),
//                                modelColumn.getColumnName(),
//                                jdbcType,
//                                javaType != null ? javaType.getName() : null
//                        });
//            }
//        }
//
//        //Primary keys
//        List<com.manydesigns.portofino.model.database.Column> columnPKList
//                = aTable.getPrimaryKey().getColumns();
//
//        if(!columnList.containsAll(columnPKList)) {
//            logger.error("Primary key refers to some invalid columns, skipping table {}", aTable.getQualifiedName());
//            return null;
//        }
//
//        if (columnPKList.size() > 1) {
//            createPKComposite(mappings, aTable, aTable.getPrimaryKey().getPrimaryKeyName(),
//                    clazz, tab, columnPKList);
//        } else {
//            createPKSingle(mappings, aTable, aTable.getPrimaryKey().getPrimaryKeyName(),
//                    clazz, tab, columnPKList);
//        }
//
//        //Other columns
//        columnList.removeAll(columnPKList);
//
//        for (com.manydesigns.portofino.model.database.Column column
//                : columnList) {
//            Column col = createColumn(mappings, tab, column);
//            if(col != null) {
//                clazz.addProperty(createProperty(column, col.getValue()));
//            }
//        }
//
//        return clazz;
//    }



//    public void annotationConfiguration(){
//        AnnotationConfiguration annotationConfig=new AnnotationConfiguration();
//        annotationConfig.setProperty("hibernate.connection.datasource","java:comp/env/jdbc/YOURJNDINAME");
//        annotationConfig.setProperty("hibernate.show_sql", "false"); //add other properties if needed
//        annotationConfig.addAnnotatedClass(YOURANNOTATEDBEANCLASS); //add all classes JPA annotated
//        org.hibernate.SessionFactory sessionFactory=annotationConfig.buildSessionFactory();
//    }



}
