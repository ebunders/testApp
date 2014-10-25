package ebunders.test.web.util;

import com.google.common.base.Optional;
import ebunders.test.domain.user.User;
import ebunders.test.domain.user.UserDao;
import org.apache.wicket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by Ernst Bunders on 16-10-14.
 */

public final class UserUtil implements Serializable{
    private final Session session;
    private static final String USER_KEY = "__user";

    public UserUtil(Session session) {
        this.session = session;
    }


    public void loginUser(User user) {
        session.setAttribute(USER_KEY, user);
    }

    public Optional<User> getLoggedinUser() {
        if(session.getAttributeNames().contains(USER_KEY)) {
            User user = (User) session.getAttribute(USER_KEY);
            return Optional.of(user);
        }
        return Optional.absent();
    }

    public void logout() {
        session.removeAttribute(USER_KEY);
    }

    public boolean isLoggedIn(){
        return getLoggedinUser().isPresent();
    }

}
