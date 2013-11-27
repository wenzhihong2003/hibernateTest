package model;

import cfg.SessionTemplate;
import org.hibernate.type.EnumType;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Properties;

/**
 * 测试枚举的例子
 * User: wenzhihong
 * Date: 13-11-27
 * Time: 上午9:19
 */
public class EnumTest extends BaseTest {
    /**
     * 测试枚举的例子
     *
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
}
