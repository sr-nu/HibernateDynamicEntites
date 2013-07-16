package com.motech.test.pojoGeneration;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPersistGeneratedPojoEntity {

    private static String tableName = "NEW_PERSON";

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Map<String, Class<?>> props = new HashMap<String, Class<?>>();
        props.put("id", Integer.class);
        props.put("user", String.class);

        Class newPersonClass = PojoGenerator.generate("com.motech.test.pojoGeneration.NewPerson", props);
        Object newPersonInstance = newPersonClass.newInstance();
        newPersonClass.getMethod("setId", Integer.class).invoke(newPersonInstance, 36);
        newPersonClass.getMethod("setUser", String.class).invoke(newPersonInstance,"testUser2");
        String className = newPersonClass.getName();


        SessionFactory sessionFactory = new DynamicEntityPersister().getSessionFactory(className, tableName, props);
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(newPersonInstance);
        transaction.commit();
        session.close();

        Session session1 = new DynamicEntityPersister().getSessionFactory(className, tableName, props).openSession();
        List list = session1.createSQLQuery("select * from "+tableName).list();

        System.out.println("Number of entities:" + list.size());

        for (Object o : list) {
            System.out.println(o);
        }

        session1.close();

        Session session2 = sessionFactory.openSession();
        List list1 = session2.createQuery("From "+ className).list();
        for (Object o : list1) {
            System.out.println(newPersonClass.getMethod("getId").invoke(o)+ " :: " + newPersonClass.getMethod("getUser").invoke(o));
        }

    }

}
