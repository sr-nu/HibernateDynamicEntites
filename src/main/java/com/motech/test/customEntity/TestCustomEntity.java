package com.motech.test.customEntity;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.util.List;

public class TestCustomEntity {

    public static void main(String[] args) {
        SessionFactory sessionFactory = new CustomEntityCreator().getSessionFactory2();
        Session session = sessionFactory.openSession();
        Person person = new Person();
        person.setId(22);
        person.setUser("testUser");
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();

//        Person fetched = (Person) session.get(person.getClass(), 21);
//
//        System.out.println(fetched.getId());
//
//        session.close();
//
//
//        Session session1 = sessionFactory.openSession();
//
//        Person newPerson = (Person) session1.get(new Person().getClass(), 21);
//
//        System.out.print(newPerson.getId());


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
