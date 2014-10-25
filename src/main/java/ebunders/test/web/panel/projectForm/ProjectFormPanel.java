package ebunders.test.web.panel.projectForm;

import ebunders.test.domain.project.Project;
import ebunders.test.domain.project.ProjectDao;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * TODO: project updates is not implemented yet
 * TODO: form validation
 * Created by Ernst Bunders on 16-10-14.
 */
public class ProjectFormPanel extends GenericPanel<Project> {

    @SpringBean
    private ProjectDao projectDao;


    public ProjectFormPanel(String id) {
        super(id, new CompoundPropertyModel<Project>(new Project()));
        setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final WebMarkupContainer panel = new WebMarkupContainer("panel");
        add(panel);

        panel.add(new Label("actionLabel", Model.of("Add a project")));
        Form<Project> form = new Form<Project>("projectForm", getModel());

                form.add(new TextField<String>("name"))
                .add(new DateTextField("dueDate", "dd-MM-yyyy"))
                .add(new AjaxSubmitLink("save") {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        handleSubmit(target, form);
                    }
                }
                        .add(new Label("actionButtonLabel", Model.of("Add"))));

        panel.add(form);
    }

    private void handleSubmit(AjaxRequestTarget target, Form<?> form) {
        Project project = (Project) form.getModelObject();
        projectDao.saveOrUpdate(project);
        this.setDefaultModelObject(new Project());
        target.add(getPage().get("projectTable"));
        target.add(getPage().get("projectForm"));
    }
}
