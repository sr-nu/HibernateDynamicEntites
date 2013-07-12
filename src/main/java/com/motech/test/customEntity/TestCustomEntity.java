package com.motech.test.customEntity;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.util.List;

public class TestCustomEntity {

    private static String tableName;

    public static void main(String[] args) {
        tableName = "PERSON";
        SessionFactory sessionFactory = new CustomEntityCreator().getSessionFactory(Person.class.getName(), tableName);
        Session session = sessionFactory.openSession();
        Person person = new Person();
        person.setId(28);
        person.setUser("testUser2");
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();

        Session session1 = sessionFactory.openSession();
        List list = session1.createSQLQuery("select * from "+tableName).list();

        System.out.println("Number of entities:"+ list.size());

        for (Object o : list) {
                System.out.println(o);
        }

        session1.close();

        Session session2 = sessionFactory.openSession();
        List list1 = session2.createQuery("From com.motech.test.customEntity.Person").list();
        for (Object o : list1) {
            System.out.println(((Person) o).getId() +" : : "+((Person) o).getUser());
        }


    }



}
