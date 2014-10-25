package ebunders.test.web.panel.projectTable;

import ebunders.test.domain.project.Project;
import ebunders.test.domain.project.ProjectDao;
import ebunders.test.domain.task.TaskDao;
import ebunders.test.web.page.projects.ProjectPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Ernst Bunders on 20-10-14.
 * TODO:sortable
 */
public final class ProjectActionPanel extends Panel {
    private final Project project;

    @SpringBean
    private TaskDao taskDao;

    @SpringBean
    private ProjectDao projectDao;

    private boolean isDeletable;

    public ProjectActionPanel(String id, Project project) {
        super(id);
        this.project = project;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        isDeletable = ! taskDao.projectIsUsed(project);
        createEditAndDeletePanel();
        createEditPanel();
    }


    private void createEditAndDeletePanel() {
        WebMarkupContainer createAndEditPanel = new WebMarkupContainer("actionsWithDelete");
        createAndEditPanel.setOutputMarkupId(true);
        createAndEditPanel.setVisible(isDeletable);

        createAndEditPanel.add(createEditLink());

        createAndEditPanel.add(new AjaxFallbackLink("delete") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                projectDao.delete(project);
                target.add(getPage().get("projectForm"));
                target.add(getPage().get("projectTable"));
            }
        });

        addOrReplace(createAndEditPanel);
    }

    private void createEditPanel() {
        WebMarkupContainer createAndEditPanel = new WebMarkupContainer("actionsNoDelete");
        createAndEditPanel.setOutputMarkupId(true);
        createAndEditPanel.setVisible(! isDeletable);

        createAndEditPanel.add(createEditLink());
        addOrReplace(createAndEditPanel);
    }

    private AjaxFallbackLink createEditLink(){
        return new AjaxFallbackLink("edit") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                System.out.println("edit");
                ((ProjectPage)getPage()).setProject(project);
                target.add(getPage().get("projectForm"));
            }
        };
    }
}
