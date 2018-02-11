package be.superjoran.mint.wicket.upload;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.mint.domain.searchresults.CsvFile;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

public class UploadCsvStep2Panel extends GenericPanel<List<CsvFile>> {
    private static final long serialVersionUID = -8480823644003013981L;

    public UploadCsvStep2Panel(String id, IModel<List<CsvFile>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTableBuilderFactory.<CsvFile, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("file"), csvFile -> csvFile.getFileUrl().getName()))
                .addColumn(new LambdaColumn<>(new ResourceModel("bank.account"), csvFile -> csvFile.getBankAccount().getName()))
                .attach(this, "datatable", this.getModel());

    }
}
