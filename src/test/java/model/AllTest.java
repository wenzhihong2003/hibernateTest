package model;

import cfg.MyNamingStrategy;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.TypeHelper;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tutorial.hbm.SessionTemplate;
import org.hibernate.type.EnumType;
import org.hibernate.type.Type;
import org.junit.*;

import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static org.hibernate.tutorial.hbm.Util.*;

/**
 * User: wenzhihong
 * Date: 11/13/13
 * Time: 1:06 PM
 */
public class AllTest {
    static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() throws Exception {
        //hibernate 3的写法
        //sessionFactory = new Configuration().configure().setNamingStrategy(MyNamingStrategy.instance).buildSessionFactory();

        //hibernate 4的写法
        Configuration configuration = new Configuration().configure().setNamingStrategy(MyNamingStrategy.instance);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }



    @Test
    public void testNewsOne2One() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                News news = new News();
                news.setTitle("第一条新闻");
                news.setPubDate(new Date());
                news.setSubTitle("子标题");
                news.setSubContent("子内容");
                news.setContent("内容...............");
                NewsDetail newsDetail = new NewsDetail();
                newsDetail.setContent("主....");
                newsDetail.setSubContent("主.....主");
                news.setNewsDetail(newsDetail);
                newsDetail.setNews(news);
                session.save(news);
            }
        }.exec(sessionFactory);

    }

    @Test
    public void testNews() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                News news = new News();
                news.setTitle("第一条新闻");
                news.setPubDate(new Date());
                news.setSubTitle("子标题");
                news.setSubContent("子内容");
                news.setContent("内容...............");

                session.save(news);

                System.out.println(news.getId());

            }
        }.exec(sessionFactory);

        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                News n = (News) session.get(News.class, 1L);
                System.out.println(n.getTitle());
                //System.out.println(n.getContent());
            }
        }.exec(sessionFactory);

    }

    @Test
    public void testWork() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                session.doWork(new Work() {
                    @Override
                    public void execute(Connection con) throws SQLException {
                        startFlag();
                        DatabaseMetaData metaData = con.getMetaData();
                        System.out.println(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
                        System.out.println(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
                        System.out.println(metaData.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));
                        endFlag();
                    }
                });
            }
        }.exec(sessionFactory);

    }

    /**
     * 测试枚举的例子
     * @throws Exception
     */
    @Test
    public void testUserEnum() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u = new CommonUser();
                u.setName("aaa");
                u.setGrade(Grade.higest);
                session.saveOrUpdate(u);
                System.out.println("-----" + u.getAge());
            }
        }.exec(sessionFactory);

        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                //在查询语句里可以直接使用枚举的实例
                User u = (User) session.createQuery("from User where grade = model.Grade.higest").uniqueResult();
                System.out.println(u.getName());
            }
        }.exec(sessionFactory);

        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                //参数化的写法
                Properties p = new Properties();
                p.setProperty("enumClass", Grade.class.getName());
                p.setProperty("useNamed", "true");
                session.createQuery("from User where grade = :grade")
                        .setParameter("grade", Grade.advance, session.getTypeHelper().custom(EnumType.class, p))
                        .uniqueResult();

            }
        }.exec(sessionFactory);

    }

    @Test
    public void testRole() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                Iterator iterate = session.createQuery("from Role ").iterate();
                while (iterate.hasNext()) {
                    Role o = (Role) iterate.next();
                    Set<UserRole> userRoles = o.getUserRoles();
                    for (UserRole userRole : userRoles) {
                        System.out.println(userRole.getUser().getName());
                    }
                }

            }
        }.exec(sessionFactory);

    }

    @Test
    public void testMulti() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                Iterator iterate = session.createQuery("from CommonUser").iterate();
                while (iterate.hasNext()) {
                    User u = (User) iterate.next();
                    System.out.println(u.getName());
                }

            }
        }.exec(sessionFactory);

    }

    @Test
    public void test1() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u1 = new MangeUser("温志宏", 30);
                u1.setMan(Boolean.TRUE);

                User u2 = new CommonUser("刘红江", 28);
                u1.setMan(Boolean.TRUE);
                u1.setUsed(Boolean.FALSE);

                User u3 = new CommonUser("小红", 12);
                u1.setMan(Boolean.FALSE);

                Role r1 = new Role("一般人员");
                Role r2 = new Role("管理员");

                UserRole ur11 = new UserRole(u1, r1);
                UserRole ur12 = new UserRole(u1, r2);

                u1.getUserRoles().add(ur11);
                u1.getUserRoles().add(ur12);

                UserRole ur21 = new UserRole(u2, r1);
                u2.getUserRoles().add(ur21);

                UserRole ur32 = new UserRole(u3, r2);
                u3.getUserRoles().add(ur32);

                session.saveOrUpdate(ur11);
                session.saveOrUpdate(ur12);
                session.saveOrUpdate(ur21);
                session.saveOrUpdate(ur32);



            }
        }.exec(sessionFactory);

    }

    @Test
    public void testAddUser() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u = new CommonUser();
                u.setName("aaaaaaaa");
                session.save(u);

                startFlag();
                System.out.println(u.getAge());
                endFlag();
            }
        }.exec(sessionFactory);


        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u = (User) session.get(User.class, 1L);
                startFlag();
                session.delete(u);
            }
        }.exec(sessionFactory);

    }
}
