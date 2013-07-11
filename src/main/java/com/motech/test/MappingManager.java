package com.motech.test;

import org.hibernate.Session;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.type.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;

public class MappingManager {
    public static void updateClassMapping(CustomizableEntityManager entityManager) {
        try {
            Session session = HibernateUtil.getInstance().getCurrentSession();
            Class<? extends CustomizableEntity> entityClass = entityManager.getEntityClass();
            String file = entityClass.getResource(entityClass.getSimpleName() + ".hbm.xml").getPath();

            Document document = XMLUtil.loadDocument(file);
            NodeList componentTags = document.getElementsByTagName("dynamic-component");
            Node node = componentTags.item(0);
            XMLUtil.removeChildren(node);

            Iterator propertyIterator = entityManager.getCustomProperties().getPropertyIterator();
            while (propertyIterator.hasNext()) {
                Property property = (Property) propertyIterator.next();
                Element element = createPropertyElement(document, property);
                node.appendChild(element);
            }

            XMLUtil.saveDocument(document, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createPropertyElement(Document document, Property property) {
        Element element = document.createElement("property");
        Type type = property.getType();

        element.setAttribute("name", property.getName());
        element.setAttribute("column", ((Column)
                property.getColumnIterator().next()).getName());
        element.setAttribute("type",
                type.getReturnedClass().getName());
        element.setAttribute("not-null", String.valueOf(false));

        return element;
    }
}
