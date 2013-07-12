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

    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        Mappings mappings = configuration.createMappings();


        Dialect dialect = Dialect.getDialect(configuration.getProperties());

        // physical model

        Table tab  = mappings.addTable("PUBLIC", null, "PERSON", null, false);

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

        tab.addColumn(id_col);

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

        mappings.addTableBinding("PUBLIC", null,"PERSON", "PERSON", null);


        return configuration.buildSessionFactory();

    }

}
