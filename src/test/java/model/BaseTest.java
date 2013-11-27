package model;

import cfg.MyNamingStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * User: wenzhihong
 * Date: 13-11-27
 * Time: 上午9:14
 */
public class BaseTest {
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
}
