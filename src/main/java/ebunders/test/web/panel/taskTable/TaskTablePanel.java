package ebunders.test.web.panel.taskTable;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.task.Task;
import ebunders.test.domain.task.TaskDao;
import ebunders.test.web.panel.projectTable.ProjectActionPanel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * TODO: show priority as star in first column
 * TODO: color code rows that are on or beyond due date.
 * Created by Ernst Bunders on 21-10-14.
 */
public class TaskTablePanel extends Panel {

    @SpringBean
    private TaskDao taskDao;

    private Panel panel = this;

    public TaskTablePanel(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        List<IColumn<Task, String>> columns = createColumns();

        final SortableTaskProvider dataProvider = new SortableTaskProvider();

        final AjaxFallbackDefaultDataTable<Task, String> table =
                new AjaxFallbackDefaultDataTable<Task, String>("table", columns, dataProvider, 10);

        add(table);
    }


    private List<IColumn<Task, String>> createColumns() {
        List<IColumn<Task, String>> columns = new ArrayList<IColumn<Task, String>>();
        columns.add(new PropertyColumn<Task, String>(Model.of("Name"), "name"));
        columns.add(new PropertyColumn<Task, String>(Model.of("Description"), "description"));
        columns.add(new PropertyColumn<Task, String>(Model.of("Due Date"), "dueDate"));
        columns.add(new PropertyColumn<Task, String>(Model.of("Priority"), "priority"));
        columns.add(new PropertyColumn<Task, String>(Model.of("Done"), "done"));
        columns.add(new ProjectColumn());
        columns.add(new AbstractColumn<Task, String>(new Model<String>("Actions")) {

            public void populateItem(Item<ICellPopulator<Task>> cellItem, String componentId, IModel<Task> model) {
                cellItem.add(new TaskActionPanel(componentId, model.getObject()));
            }
        });
        return columns;
    }


    /**
     * Sortable data provider
     */
    private class SortableTaskProvider extends SortableDataProvider<Task, String> {

        private SortableTaskProvider() {
            setSort(new SortParam<String>("name", true));

        }

        @Override
        public Iterator<? extends Task> iterator(long first, long count) {
            List<Task> projects = new ArrayList<Task>();
            int counter = 0, amount = 0;
            for (Iterator<Task> pi = taskDao.getAll(); pi.hasNext(); ) {
                Task project = pi.next();
                if (counter++ >= first && amount++ <= count) {
                    projects.add(project);
                }
            }
            return projects.iterator();
        }

        @Override
        public long size() {
            return Lists.newArrayList(taskDao.getAll()).size();
        }

        @Override
        public IModel<Task> model(Task task) {
            return new DetachableTaskModel(task);
        }
    }


    /**
     * TODO: abstract this into a LoadableDetachableModel for type Identifiable and reuse it in the Project table panel
     * Table model
     */
    private class DetachableTaskModel extends LoadableDetachableModel<Task> {
        private String taskId;

        public DetachableTaskModel(Task task) {
            this.taskId = task.getId();
        }


        @Override
        protected Task load() {
            Optional<Task> optional = taskDao.findById(taskId);
            if(optional.isPresent()) {
                return optional.get();
            }
            throw new RuntimeException("Task with id " + taskId + " not found");
        }
    }

    /**
     *
     */
    private final class ProjectColumn extends AbstractColumn<Task, String> {
        public ProjectColumn() {
            super(Model.of("Project"), "project");
        }

        @Override
        public void populateItem(Item<ICellPopulator<Task>> cellItem, String componentId, IModel<Task> rowModel) {
            Project project = rowModel.getObject().getProject();
            String projectName = "-- --";
            if (project != null) {
                projectName = project.getName();
            }
            cellItem.add(new Label(componentId, projectName));
        }
    }
}
