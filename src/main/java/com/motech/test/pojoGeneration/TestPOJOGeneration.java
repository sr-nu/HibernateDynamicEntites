package com.motech.test.pojoGeneration;

import com.motech.test.customEntity.CustomEntityCreator;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPOJOGeneration {

    private static String tableName;

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Map<String, Class<?>> props = new HashMap<String, Class<?>>();
        props.put("id", Integer.class);
        props.put("user", String.class);

        Class person = PojoGenerator.generate("com.motech.test.pojoGeneration.NewPerson", props);
        System.out.println(person);
        System.out.println(person.getName());


        tableName = "NEW_PERSON";
        SessionFactory sessionFactory = new CustomEntityCreator().getSessionFactory(person.getName(), tableName);
        Session session = sessionFactory.openSession();
        Object personInstance = person.newInstance();

        person.getMethod("setId",Integer.class).invoke(personInstance, 34);
        person.getMethod("setUser",String.class).invoke(personInstance,"testUser2");
        Transaction transaction = session.beginTransaction();
        session.save(personInstance);
        transaction.commit();

        Session session1 = sessionFactory.openSession();
        List list = session1.createSQLQuery("select * from "+tableName).list();

        System.out.println("Number of entities:"+ list.size());

        for (Object o : list) {
            System.out.println(o);
        }

        session1.close();

        Session session2 = sessionFactory.openSession();
        List list1 = session2.createQuery("From "+person.getName()).list();
        for (Object o : list1) {
            System.out.println(person.getMethod("getId").invoke(o)+ " :: " + person.getMethod("getUser").invoke(o));
        }



    }



}
