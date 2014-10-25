package ebunders.test.web.page;

import ebunders.test.web.page.login.LoginPage;
import ebunders.test.web.page.projects.ProjectPage;
import ebunders.test.web.page.tasks.TaskPage;
import ebunders.test.web.panel.login.LoginContentPanel;
import ebunders.test.web.util.UserUtil;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
    protected final UserUtil userUtil;

    public HomePage(final PageParameters parameters) {
		super(parameters);
        userUtil = new UserUtil(getSession());

		add(new LoginContentPanel("headerContent"));

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        createNavigation();
        if(!userUtil.isLoggedIn() && ! this.getClass().equals(LoginPage.class)) {
            setResponsePage(LoginPage.class);
        }
    }


    //TODO: this is no way to do navigation... org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel
    private void createNavigation() {
        createNavigationForPage(ProjectPage.class, "itemProjects", "linkProjects");
        createNavigationForPage(TaskPage.class, "itemTasks", "linkTasks");
    }

    private void createNavigationForPage(final Class pageClass, String itemName, String linkName){
        WebMarkupContainer navContainer = new WebMarkupContainer(itemName);
        navContainer.add(new BookmarkablePageLink<WebPage>(linkName, pageClass));

        if (getPage().getClass().equals(pageClass)) {
            navContainer.add(new AttributeModifier("class", Model.of("active")));
        }else{
            navContainer.add(new AttributeModifier("class", Model.of("")));
        }
        add(navContainer);
    }

}
