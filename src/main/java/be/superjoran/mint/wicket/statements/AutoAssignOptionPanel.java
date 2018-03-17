package be.superjoran.mint.wicket.statements;

import be.superjoran.common.model.LoadableListModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.AutomaticallyAssignOption;
import be.superjoran.mint.services.DestinationCategoryService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class AutoAssignOptionPanel extends GenericPanel<Person> {

    private final IModel<List<AutomaticallyAssignOption>> listModel;

    public AutoAssignOptionPanel(String id, IModel<Person> model) {
        super(id, model);
        this.listModel = new CategoryMapModel(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new ListView<AutomaticallyAssignOption>("list", this.listModel) {
            @Override
            protected void populateItem(ListItem<AutomaticallyAssignOption> item) {
                item.add(new Label("label", new StringResourceModel("automatically.assign.option", item.getModel())));
            }
        });
    }

    private static final class CategoryMapModel extends LoadableListModel<AutomaticallyAssignOption> {
        private static final long serialVersionUID = 551284651537968795L;
        private final IModel<Person> personModel;
        @SpringBean
        private DestinationCategoryService destinationCategoryService;

        public CategoryMapModel(IModel<Person> personModel) {
            this.personModel = personModel;
        }

        @Override
        protected List<AutomaticallyAssignOption> load() {
            return this.destinationCategoryService.findCategoriesThatHavePossibleCandidates(this.personModel.getObject());
        }


    }
}
