package com.motech.test;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

public class HibernateUtil {

    private static HibernateUtil instance;
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;

    public synchronized static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory();
        }
        return sessionFactory;
    }

    public synchronized Session getCurrentSession() {
        if (session == null) {
            session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.COMMIT);
            System.out.println("session opened.");
        }
        return session;
    }

    public synchronized Configuration getConfiguration() {
        if (configuration == null) {
            System.out.print("configuring Hibernate ... ");
            try {
                configuration = new Configuration().configure();
                configuration.addClass(Contact.class);
                System.out.println("ok");
            } catch (HibernateException e) {
                System.out.println("failure");
                e.printStackTrace();
            }
        }
        return configuration;
    }
    public void reset() {
        Session session = getCurrentSession();
        if (session != null) {
            session.flush();
            if (session.isOpen()) {
                System.out.print("closing session ... ");
                session.close();
                System.out.println("ok");
            }
        }
        SessionFactory sf = getSessionFactory();
        if (sf != null) {
            System.out.print("closing session factory ... ");
            sf.close();
            System.out.println("ok");
        }
        this.configuration = null;
        this.sessionFactory = null;
        this.session = null;
    }

    public PersistentClass getClassMapping(Class entityClass){
        return getConfiguration().getClassMapping(entityClass.getName());
    }
}