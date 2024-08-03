package ru.savin.servlet.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.savin.servlet.model.Event;
import ru.savin.servlet.model.File;
import ru.savin.servlet.model.User;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory = null;

    private static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            sessionFactory = new Configuration().addAnnotatedClass(Event.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(User.class).buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
