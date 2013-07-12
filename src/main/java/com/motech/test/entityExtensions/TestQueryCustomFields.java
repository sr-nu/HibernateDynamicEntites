package com.motech.test.entityExtensions;


import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class TestQueryCustomFields {
    public static void main(String[] args) {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        Criteria criteria = session.createCriteria(Contact.class);
        criteria.add(Restrictions.eq(CustomizableEntityManager.CUSTOM_COMPONENT_NAME + ".email", "test@test.com"));
        List list = criteria.list();
        System.out.println("list.size() = " + list.size());
    }
}
