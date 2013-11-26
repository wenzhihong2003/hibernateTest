package org.hibernate.tutorial.hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.MalformedURLException;

/**
 * User: wenzhihong
 * Date: 10/22/13
 * Time: 10:01 AM
 */
public abstract class SessionTemplate {
    protected Session session;

    public void exec(SessionFactory sessionFactory){
        session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            doInSession();
            commit();
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    protected abstract void doInSession() throws MalformedURLException;

    protected void commit() {
        session.getTransaction().commit();
    }

    protected void rollback() {
        session.getTransaction().rollback();
    }

    protected void beginTransaction() {
        session.beginTransaction();
    }


}
