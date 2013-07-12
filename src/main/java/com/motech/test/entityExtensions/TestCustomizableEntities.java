package com.motech.test.entityExtensions;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;

public class TestCustomizableEntities {
    private static final String TEST_FIELD_NAME = "email";
    private static final String TEST_VALUE = "test@test.com";

    public static void main(String[] args) {
        HibernateUtil.getInstance().getCurrentSession();

        CustomizableEntityManager contactEntityManager = new
                CustomizableEntityManagerImpl(Contact.class);

        contactEntityManager.addCustomField(TEST_FIELD_NAME);







        Session session = HibernateUtil.getInstance().getCurrentSession();

        Transaction tx = session.beginTransaction();
        try {

            Contact contact = new Contact();
            contact.setName("Contact Name 1");
            contact.setValueOfCustomField(TEST_FIELD_NAME, TEST_VALUE);



            Serializable id = session.save(contact);
            tx.commit();

            contact = (Contact) session.get(Contact.class, id);
            Object value = contact.getValueOfCustomField(TEST_FIELD_NAME);
            System.out.println("value = " + value);

        } catch (Exception e) {
            tx.rollback();
            System.out.println("e = " + e);
        }
    }
}