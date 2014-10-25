package ebunders.test.web.panel.taskForm;

import com.google.common.collect.Lists;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.project.ProjectDao;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * Created by Ernst Bunders on 21-10-14.
 */
public class TaskFormPanel extends GenericPanel<Task> {

    @SpringBean
    private ProjectDao projectDao;

    @SpringBean
    private TaskDao taskDao;

    public TaskFormPanel(String id) {
        super(id, new CompoundPropertyModel<Task>(new Task()));
        setOutputMarkupId(true);
    }

    //TODO: validation
    @Override
    protected void onInitialize() {
        super.onInitialize();

        final WebMarkupContainer panel = new WebMarkupContainer("panel");
        add(panel);

        panel.add(new Label("actionLabel", Model.of("Add a project")));
        Form<Task> form = new Form<Task>("taskForm", getModel());

        form.add(new TextField<String>("name"))
                .add(new DateTextField("dueDate", "dd-MM-yyyy"))
                .add(new TextArea<String>("description"))
                .add(new CheckBox("priority"))
                .add(createSelect("project"))

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
        Task task = (Task) form.getModelObject();
        taskDao.saveOrUpdate(task);
        setDefaultModelObject(new Task());

        target.add(getPage().get("taskForm"));
        target.add(getPage().get("taskTable"));
    }


    private Component createSelect(String id) {
        LoadableDetachableModel<List<Project>> projectModel = new LoadableDetachableModel<List<Project>>() {
            @Override
            protected List<Project> load() {
                return Lists.newArrayList(projectDao.getAll());
            }
        };

        ChoiceRenderer<Project> choiceRenderer = new ChoiceRenderer<Project>("name", "id");

        return new DropDownChoice<Project>(id, projectModel, choiceRenderer);
    }
}
