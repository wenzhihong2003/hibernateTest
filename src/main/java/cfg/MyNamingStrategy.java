package cfg;

import com.google.common.base.CaseFormat;
import org.hibernate.cfg.EJB3NamingStrategy;
import org.hibernate.internal.util.StringHelper;

/**
 * User: wenzhihong
 * Date: 10/28/13
 * Time: 9:57 AM
 */
public class MyNamingStrategy extends EJB3NamingStrategy {
    public static final MyNamingStrategy instance = new MyNamingStrategy();

    @Override
    public String classToTableName(String className) {
        String shortName = StringHelper.unqualify(className);
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, shortName);
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, propertyName);
    }
}
