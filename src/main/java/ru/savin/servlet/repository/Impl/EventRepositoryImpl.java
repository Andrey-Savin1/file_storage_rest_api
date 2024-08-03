package ru.savin.servlet.repository.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.savin.servlet.model.Event;
import ru.savin.servlet.repository.EventRepository;
import ru.savin.servlet.util.HibernateSessionFactory;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private final Session newSession = HibernateSessionFactory.getSession();

    @Override
    public Event create(Event event) {
        Session session = newSession;
        session.beginTransaction();
        session.persist(event);
        session.getTransaction().commit();
        return event;
    }

    @Override
    public Event update(Event writer) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        var mergeEvent = session.merge(writer);
        transaction.commit();
        return mergeEvent;
    }

    @Override
    public Event getById(Long id) {
        Session session = newSession;
        var result = session.get(Event.class, id);
        return result;
    }

    @Override
    public List<Event> getAll() {
        var allUser = newSession.createQuery("select w from Event w", Event.class).getResultList();
        return allUser;
    }

    @Override
    public void deleteById(Long id) {
        Session session = newSession;
        session.beginTransaction();
        Event event = session.get(Event.class, id);
        session.remove(event);
        session.getTransaction().commit();

    }
}
