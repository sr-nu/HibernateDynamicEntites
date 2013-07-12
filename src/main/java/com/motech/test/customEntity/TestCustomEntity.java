package com.motech.test.customEntity;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.util.List;

public class TestCustomEntity {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new CustomEntityCreator().getSessionFactory();
        Session session = sessionFactory.openSession();
        Person person = new Person();
        person.setId(23);
        person.setUser("testUser");
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();

        Session session1 = sessionFactory.openSession();
        List list = session1.createSQLQuery("select * from Person").list();

        System.out.println("Number of entities:"+ list.size());

        for (Object o : list) {
            {
                System.out.println(o);
            }

        }


    }
}
