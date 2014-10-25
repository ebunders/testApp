package ebunders.test.web.page.projects;

import ebunders.test.domain.project.Project;
import ebunders.test.web.page.HomePage;
import ebunders.test.web.page.login.LoginPage;
import ebunders.test.web.util.UserUtil;
import ebunders.test.web.panel.projectForm.ProjectFormPanel;
import ebunders.test.web.panel.projectTable.ProjectTabelPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by Ernst Bunders on 16-10-14.
 */
public class ProjectPage extends HomePage {

    private ProjectFormPanel projectFormPanel;



    public ProjectPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        projectFormPanel = new ProjectFormPanel("projectForm");
        projectFormPanel.setDefaultModelObject(new Project());

        add(projectFormPanel);
        add(new ProjectTabelPanel("projectTable"));
    }

    public void setProject(Project project) {
        projectFormPanel.setDefaultModelObject(project);
    }

}
