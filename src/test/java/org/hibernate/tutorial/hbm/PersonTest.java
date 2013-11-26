package org.hibernate.tutorial.hbm;

import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Set;

import static org.hibernate.criterion.Restrictions.*;

/**
 * User: wenzhihong
 * Date: 10/28/13
 * Time: 5:33 PM
 */
public class PersonTest {
    SessionFactory sessionFactory;
    @Before
    public void setUp() throws Exception {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testUser() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                {
                    Group g = new Group("第一组");
                    g.addPerson(new Person("wen", "zhihong"));
                    g.addPerson(new Person("liu", "hongjiang"));
                    g.addPerson(new Person("liu", "jianli"));

                    session.saveOrUpdate(g);
                }
                {
                    Group g = new Group("第二组");
                    g.addPerson(new Person("wen2", "zhihong2"));
                    g.addPerson(new Person("liu2", "hongjiang2"));
                    g.addPerson(new Person("liu2", "jianli2"));

                    session.saveOrUpdate(g);
                }
                {
                    Group g = new Group("第3组");
                    g.addPerson(new Person("wen3", "zhihong3"));
                    g.addPerson(new Person("liu3", "hongjiang3"));
                    g.addPerson(new Person("liu3", "jianli3"));

                    session.saveOrUpdate(g);
                }
                {
                    Group g = new Group("第4组");
                    g.addPerson(new Person("wen4", "zhihong4"));
                    g.addPerson(new Person("liu4", "hongjiang4"));
                    g.addPerson(new Person("liu4", "jianli4"));

                    session.saveOrUpdate(g);
                }

            }
        }.exec(sessionFactory);

        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                Group g = (Group) session.get(Group.class, 1L);
                session.delete(g);
            }
        }.exec(sessionFactory);

    }

    @Test
    public void testQuery() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                session.get(Group.class, 1L);
            }
        }.exec(sessionFactory);

    }

    @Test
    public void testCriteria() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                /*Person p = (Person) session.createCriteria(Person.class)
                        .setFetchMode("group", FetchMode.JOIN)
                        .add(not(between("id", 3L, 20L)))
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println(p);*/

                Group g = (Group) session.createCriteria(Group.class)
                        .add(isEmpty("persons"))

                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println(g);
            }
        }.exec(sessionFactory);

    }

    @Test
    public void testQuery1() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                Person p = (Person) session.get(Person.class, 1L);
                Group group = p.getGroup();
                System.out.println(group.getName());
                Set<Person> persons = group.getPersons();
                for (Person p2 : persons) {
                    System.out.println(p2.getFirstName());
                }
            }
        }.exec(sessionFactory);
    }

    @Test
    public void testReadOnly() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                session.setDefaultReadOnly(true);
                Person p = (Person) session.get(Person.class, 1L);
                session.setReadOnly(p, false);
                p.setFirstName("3333");
                p.setFirstName("4444");
                session.flush();

            }
        }.exec(sessionFactory);

    }

    @Test
    public void testLock() throws Exception {
        final Person[] person = new Person[1];
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                person[0] = (Person) session.get(Person.class, 1L);
            }
        }.exec(sessionFactory);

        person[0].setFirstName("999");

        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                session.buildLockRequest(LockOptions.READ).lock(person[0]);
                //session.update(person[0]);
            }
        }.exec(sessionFactory);
    }
}
