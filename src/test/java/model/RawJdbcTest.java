package model;

import cfg.SessionTemplate;
import org.hibernate.jdbc.Work;
import org.junit.Test;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cfg.Util.endFlag;
import static cfg.Util.startFlag;

/**
 * 直接使用jdbc connection的测试例子
 * User: wenzhihong
 * Date: 13-11-27
 * Time: 上午9:18
 */
public class RawJdbcTest extends BaseTest {

    //这里用上work,就可以直接用jdbc的connection了
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

}
