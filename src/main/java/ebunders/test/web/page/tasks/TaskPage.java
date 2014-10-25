package ebunders.test.web.page.tasks;

import ebunders.test.domain.task.Task;
import ebunders.test.web.page.HomePage;
import ebunders.test.web.panel.taskForm.TaskFormPanel;
import ebunders.test.web.panel.taskTable.TaskTablePanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by Ernst Bunders on 16-10-14.
 */
public class TaskPage extends HomePage {
    public TaskPage(PageParameters parameters) {
        super(parameters);
    }

    private Panel taskForm;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.taskForm = new TaskFormPanel("taskForm");
        add(taskForm);
        add(new TaskTablePanel("taskTable"));
    }

    public void setTask(Task task) {
        taskForm.setDefaultModelObject(task);
    }
}


