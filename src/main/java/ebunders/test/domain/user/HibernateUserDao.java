package ebunders.test.domain.user;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import ebunders.test.common.domain.HibernateBaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Ernst Bunders on 21-10-14.
 */
public class HibernateUserDao extends HibernateBaseDao<User> implements UserDao {

    @Override
    @Transactional
    public Optional<User> findByUsernameAndPassword(String userName, String password) {
        Map<String, String> params = ImmutableMap.of("userName", userName, "password", password);

        return getByParameters("userName", userName);
    }
}
