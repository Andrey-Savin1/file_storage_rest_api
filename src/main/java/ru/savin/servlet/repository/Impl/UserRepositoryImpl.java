package ru.savin.servlet.repository.Impl;


import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.savin.servlet.model.User;
import ru.savin.servlet.repository.UserRepository;
import ru.savin.servlet.util.HibernateSessionFactory;

import java.util.List;


public class UserRepositoryImpl implements UserRepository {

    private final Session newSession = HibernateSessionFactory.getSession();

    @Override
    public User create(User user) {
        Session session = newSession;
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        return user;
    }

    @Override
    public User update(User user) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        var mergeUser = session.merge(user);
        transaction.commit();
        return mergeUser;
    }

    @Override
    public User getById(Long id) {
        return newSession.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        Session session = newSession;
        var s = session.createQuery("select a from User a", User.class).getResultList();
        return s;
    }

    @Override
    public void deleteById(Long id) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.remove(user);
        transaction.commit();
    }
}
