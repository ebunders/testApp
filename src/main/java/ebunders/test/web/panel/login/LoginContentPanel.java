package ebunders.test.web.panel.login;

import com.google.common.base.Optional;
import ebunders.test.domain.user.User;
import ebunders.test.domain.user.UserDao;
import ebunders.test.web.util.FocusOnLoadBehavior;
import ebunders.test.web.util.UserUtil;
import ebunders.test.web.page.login.LoginPage;
import ebunders.test.web.page.projects.ProjectPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Ernst Bunders on 16-10-14.
 */
public final class LoginContentPanel extends Panel {

    @SpringBean
    private UserDao userDao;

    private UserUtil userUtil;

    private final Panel loginPanel;
    private String userName;
    private String password;



    public LoginContentPanel(String id) {
        super(id);
        loginPanel = this;
        this.setOutputMarkupId(true);
        userUtil = new UserUtil(getSession());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        createLoggedInPanel();
        createLogInPanel();
    }

    private void createLogInPanel() {
        final WebMarkupContainer logInContainer = new WebMarkupContainer("login");
        logInContainer.setVisible(!userUtil.isLoggedIn());
        addOrReplace(logInContainer);

        final Form loginForm = new Form("loginForm");
        final TextField<String> userNameInput = new TextField<String>("userName", new PropertyModel<String>(this, "userName"));
        userNameInput.add(new FocusOnLoadBehavior());

        loginForm.add(
                userNameInput,
                new PasswordTextField("password", new PropertyModel<String>(this, "password")),
                new AjaxSubmitLink("login") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        Optional<User> userOption = userDao.findByUsernameAndPassword(getUserName(), getPassword());
                        if (userOption.isPresent()) {
                            userUtil.loginUser(userOption.get());
                            setResponsePage(ProjectPage.class, new PageParameters());
                        } else {
                            error("No user found with given credentials");
                            target.add(loginPanel);
                            target.addChildren(getPage(), FeedbackPanel.class);
                        }

                    }
                });


        logInContainer.add(loginForm);

    }

    private void createLoggedInPanel() {
        String userName = userUtil.getLoggedinUser().or(new User()).getName();
        WebMarkupContainer loggedInContainer = new WebMarkupContainer("loggedIn");
        loggedInContainer.setVisible(userUtil.isLoggedIn());
        addOrReplace(loggedInContainer);

        loggedInContainer.add(
                new WebMarkupContainer("userWelcome")
                        .add(new Label("welcomeMessage", Model.of("hallo " + userName)))
                        .add(new AjaxLink("logout") {
                                    @Override
                                    public void onClick(AjaxRequestTarget target) {
                                        userUtil.logout();
                                        setResponsePage(LoginPage.class);
                                    }
                                }
                        )
        );
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
