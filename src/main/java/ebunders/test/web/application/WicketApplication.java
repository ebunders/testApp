package ebunders.test.web.application;

import ebunders.test.web.page.login.LoginPage;
import ebunders.test.web.page.projects.ProjectPage;
import ebunders.test.web.page.tasks.TaskPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 */
public class WicketApplication extends WebApplication {


	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return LoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        mountPage("/login", LoginPage.class);
        mountPage("/projects", ProjectPage.class);
        mountPage("/tasks", TaskPage.class);

	}
}
