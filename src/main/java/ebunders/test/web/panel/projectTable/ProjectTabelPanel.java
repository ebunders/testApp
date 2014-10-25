package ebunders.test.web.panel.projectTable;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import ebunders.test.domain.project.Project;
import ebunders.test.domain.project.ProjectDao;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.*;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * Created by Ernst Bunders on 16-10-14.
 */
public class ProjectTabelPanel extends Panel {

    @SpringBean
    private ProjectDao projectDao;

    public ProjectTabelPanel(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        List<IColumn<Project, String>> columns = createColumns();

        final SortableProjectProvider dataProvider = new SortableProjectProvider();

        final AjaxFallbackDefaultDataTable<Project, String> table =
                new AjaxFallbackDefaultDataTable<Project, String>("table", columns, dataProvider, 2);

        add(table);
    }

    private List<IColumn<Project, String>> createColumns() {
        List<IColumn<Project, String>> columns = new ArrayList<IColumn<Project, String>>();
        columns.add(new PropertyColumn<Project, String>(Model.of("Name"), "name"));
        columns.add(new PropertyColumn<Project, String>(Model.of("Due Date"), "dueDate"));
        columns.add(new AbstractColumn<Project, String>(new Model<String>("Actions")) {
            public void populateItem(Item<ICellPopulator<Project>> cellItem, String componentId, IModel<Project> model) {
                cellItem.add(new ProjectActionPanel(componentId, model.getObject()));
            }
        });
        return columns;
    }


    /**
     * Sortable data provider
     */
    private class SortableProjectProvider extends SortableDataProvider<Project, String>{

        private SortableProjectProvider () {
            setSort(new SortParam<String>("name", true));

        }

        @Override
        public Iterator<? extends Project> iterator(long first, long count) {
            List<Project> projects = new ArrayList<Project>();
            int counter = 0, amount = 0;
            for (Iterator<Project> pi = projectDao.getAll(); pi.hasNext(); ) {
                Project project = pi.next();
                if (counter++ >= first && amount++ <= count) {
                    projects.add(project);
                }
            }
            return projects.iterator();
        }

        @Override
        public long size() {
            return Lists.newArrayList(projectDao.getAll()).size();
        }

        @Override
        public IModel<Project> model(Project project) {
            return new DetetchableProjectModel(project);
        }
    }

    /**
     * Table model
     */
    private class DetetchableProjectModel extends LoadableDetachableModel<Project>{
        private String projectId;

        /**
         * @param project
         */
        public DetetchableProjectModel(Project project) {
            this.projectId = project.getId();
        }


        @Override
        protected Project load() {
            Optional<Project> optional = projectDao.findById(projectId);
            if(optional.isPresent()) {
                return optional.get();
            }
            throw new RuntimeException("Project with id " + projectId + " not found");
        }
    }


}
