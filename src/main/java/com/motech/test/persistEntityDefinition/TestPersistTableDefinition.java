package com.motech.test.persistEntityDefinition;

import com.motech.test.entityExtensions.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

public class TestPersistTableDefinition {

    public static void main(String[] args) {
        Configuration configuration = HibernateUtil.getInstance().getConfiguration(TableDefinition.class).configure();
        Session currentSession = configuration.buildSessionFactory().openSession();



        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName("test_name");
        tableDefinition.setTableDefinition("test_definition");


        Serializable serializable = currentSession.save(tableDefinition);
        currentSession.flush();


    }
}
