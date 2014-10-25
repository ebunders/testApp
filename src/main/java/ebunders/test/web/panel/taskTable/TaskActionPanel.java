package ebunders.test.web.panel.taskTable;

import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import ebunders.test.web.page.tasks.TaskPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Ernst Bunders on 20-10-14.
 * TODO:sortable
 */
public final class TaskActionPanel extends Panel {
    private final Task task;

    @SpringBean
    private TaskDao taskDao;

        private boolean isDeletable;

    public TaskActionPanel(String id, Task task) {
        super(id);
        this.task = task;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        createActionsPanel();
    }


    private void createActionsPanel() {
        WebMarkupContainer actionsPanel = new WebMarkupContainer("actions");
        add(actionsPanel);
        actionsPanel.add(createEditLink("edit"));
        actionsPanel.add(createDeleteLink("delete"));

    }

    private AjaxFallbackLink createDeleteLink(String id) {
        return new AjaxFallbackLink(id) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                taskDao.delete(task);
                target.add(getPage().get("projectForm"));
                target.add(getPage().get("projectTable"));
            }
        };
    }


    private AjaxFallbackLink createEditLink(String id){
        return new AjaxFallbackLink(id) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                System.out.println("edit");
                ((TaskPage)getPage()).setTask(task);
                target.add(getPage().get("taskForm"));
            }
        };
    }
}
