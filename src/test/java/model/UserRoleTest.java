package model;

import cfg.SessionTemplate;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static cfg.Util.*;

/**
 * User: wenzhihong
 * Date: 11/13/13
 * Time: 1:06 PM
 */
public class UserRoleTest extends BaseTest {

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

    /**
     * hibernate的继承映射测试
     * @throws Exception
     */
    @Test
    public void testUserExtendMapping() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                for (int i = 0; i < 10; i++) {
                    User u;
                    if (i % 2 == 0) {
                        u = new CommonUser("commonU" + i, i);
                    } else {
                        u = new MangeUser("mangeU" + i, i);
                    }
                    session.save(u);
                }
            }
        }.exec(sessionFactory);

        //查找 commonUser, 生成的sql会自动加上类型
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                //这里的iterator方法会先查询id, 然后按每个id产生一条sql去查询完整的信息
                Iterator iterate = session.createQuery("from CommonUser").iterate();
                while (iterate.hasNext()) {
                    User u = (User) iterate.next();
                    log(String.format("user's id=[%d], name=[%s]", u.getId(), u.getName()));
                }
            }
        }.exec(sessionFactory);


        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                //查找 MangeUser, 生成的sql会自动加上类型
                List<User> list = session.createQuery("from MangeUser").list();
                for (User u : list) {
                    log(String.format("user's id=[%d], name=[%s]", u.getId(), u.getName()));
                }


                startFlag("查询全部用户");
                List<User> listAll = session.createQuery("from User ").list();
                for (User u : listAll) {
                    log(String.format("user's id=[%d], name=[%s]", u.getId(), u.getName()));
                }
                endFlag();
            }

        }.exec(sessionFactory);
    }

    /**
     * 测试user与role的关联关联测试
     * @throws Exception
     */
    @Test
    public void testUserRoleRelation() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u1 = new MangeUser("wen66", 30);
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

    /**
     * 增加删除用户测试
     * @throws Exception
     */
    @Test
    public void testAddAndDeleteUser() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u = new CommonUser();
                u.setName("wen66");
                session.save(u);

                startFlag();
                //user的age配制的是数据库生成的默认值, 这里打印出来看是否从数据库加载了这个值
                log(String.format("user's age = %d", u.getAge()));
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

    /**
     * 乐观锁的版本测试
     * @throws Exception
     */
    @Test
    public void testVersion() throws Exception {
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                User u = new CommonUser();
                                u.setName("liuhongjiang");
                                session.save(u);
            }
        }.exec(sessionFactory);

        final User[] u = {null};
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                u[0] = (User) session.get(User.class, 1L);
            }
        }.exec(sessionFactory);

        //这里强制设置版本,要在游离对象里设置,如果在托管对象上设置, 则hibernate的机制不会让version生效.
        u[0].setVersion(20);

        //在次变成托管对象
        new SessionTemplate() {
            @Override
            protected void doInSession() throws MalformedURLException {
                session.update(u[0]);
                //这里会抛出异常,说明数据已过期, 因为version被变为20的原因
                //org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction
                //(or unsaved-value mapping was incorrect
            }
        }.exec(sessionFactory);
    }
}
