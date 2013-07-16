package com.motech.test.pojoGeneration;

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
import java.util.Map;

public class DynamicEntityPersister {

    public SessionFactory getSessionFactory(String className, String tableName, Map<String, Class<?>> props) {


        Configuration configuration = new Configuration().configure();
        Mappings mappings = configuration.createMappings();


        Dialect dialect = Dialect.getDialect(configuration.getProperties());

        // physical model

        Table tab  = mappings.addTable("PUBLIC", null, tableName, null, false);

        Column id_col = new Column("id");
        id_col.setSqlType(dialect.getTypeName(Types.INTEGER));

        SimpleValue id_val = new SimpleValue(mappings, tab);
        id_val.setTypeName("int");
        id_val.addColumn(id_col);


        Property id_prop = new Property();
        id_prop.setName("id");
        id_prop.setValue(id_val);
        id_prop.setGeneration(PropertyGeneration.INSERT);


        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setName("pkey");
        primaryKey.setTable(tab);
        primaryKey.addColumn(id_col);


        SimpleValue name_val = new SimpleValue(mappings, tab);
        name_val.setTypeName("java.lang.String");

        Column name_col = new Column("USER_NAME");
        name_col.setSqlType(dialect.getTypeName(Types.VARCHAR,255,0,0));
        name_col.setValue(name_val);

        name_val.addColumn(name_col);

        Property name_prop = new Property();
        name_prop.setName("user");
        name_prop.setValue(name_val);



        tab.addColumn(id_col);
        tab.setPrimaryKey(primaryKey);

        tab.addColumn(name_col);


        RootClass clazz = new RootClass();
        clazz.setClassName(className);
        clazz.setProxyInterfaceName(className);
        clazz.setEntityName(className);
        clazz.setJpaEntityName(className);
        clazz.setTable(tab);
        clazz.setIdentifier(id_val);
        clazz.setIdentifierProperty(id_prop);
        clazz.addProperty(name_prop);


        mappings.addClass(clazz);

        mappings.addTableBinding("PUBLIC", null, tableName, tableName, null);


        return configuration.buildSessionFactory();

    }

}
