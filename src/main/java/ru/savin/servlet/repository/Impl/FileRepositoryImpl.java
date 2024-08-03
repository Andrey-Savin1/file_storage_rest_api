package ru.savin.servlet.repository.Impl;


import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.savin.servlet.model.File;
import ru.savin.servlet.repository.FileRepository;
import ru.savin.servlet.util.HibernateSessionFactory;

import java.util.List;


public class FileRepositoryImpl implements FileRepository {

    private final Session newSession = HibernateSessionFactory.getSession();

    @Override
    public File create(File file) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        session.persist(file);
        transaction.commit();
        return file;
    }

    @Override
    public File update(File post) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        var mergePost = session.merge(post);
        transaction.commit();
        return mergePost;
    }

    @Override
    /// left join a.labels
    public File getById(Long id) {
        var result = newSession.createQuery("select a from File a where a.id = :id", File.class)
                .setParameter("id", id)
                .uniqueResult();
        return result;
    }

    @Override
    public List<File> getAll() {
        var result = newSession.createQuery("select a from File a", File.class).getResultList();
        return result;
    }

    @Override
    public void deleteById(Long id) {
        Session session = newSession;
        Transaction transaction = session.beginTransaction();
        File file = session.get(File.class, id);
        session.remove(file);
        transaction.commit();
    }
}
