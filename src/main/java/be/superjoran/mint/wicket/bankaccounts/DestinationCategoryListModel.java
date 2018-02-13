package be.superjoran.mint.wicket.bankaccounts;

import be.superjoran.common.model.LoadableListModel;
import be.superjoran.mint.domain.searchresults.DestinationCategory;
import be.superjoran.mint.services.DestinationCategoryService;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class DestinationCategoryListModel extends LoadableListModel<DestinationCategory> {
    @SpringBean
    private DestinationCategoryService destinationCategoryService;

    @Override
    protected List<DestinationCategory> load() {
        return this.destinationCategoryService.findDestinationCategories();
    }
}
