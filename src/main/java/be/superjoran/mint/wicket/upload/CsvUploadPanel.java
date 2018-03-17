package be.superjoran.mint.wicket.upload;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import java.util.List;

public class CsvUploadPanel extends GenericPanel<Person> {
    private static final long serialVersionUID = 1120537515453629009L;

    public CsvUploadPanel(String id, IModel<Person> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<List<CsvFile>> listIModel = new ListModel<>(null);
        this.add(new UploadCsvStep1Panel("step1", listIModel, this.getModel()));

        this.add(new UploadCsvStep2Panel("step2", listIModel, this.getModel()));
    }
}
