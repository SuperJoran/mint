package be.superjoran.mint.wicket.upload;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.services.CsvService;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.fileinput.BootstrapFileInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UploadCsvPanel extends GenericPanel<List<FileUpload>> {
    private static final long serialVersionUID = 1258816737254458185L;
    private static final Logger LOG = LogManager.getLogger();

    private static final String UPLOAD_FOLDER = "csvFiles";
    private final IModel<List<CsvFile>> csvFilesModel;
    private final IModel<Person> personModel;

    @SpringBean
    private CsvService csvService;

    public UploadCsvPanel(String id, IModel<List<CsvFile>> csvFilesModel, IModel<Person> personModel) {
        super(id, new ListModel<>());
        this.csvFilesModel = csvFilesModel;
        this.personModel = personModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BaseForm<List<FileUpload>> form = new BaseForm<>("form", this.getModel());

        BootstrapFileInput fileUpload = new BootstrapFileInput("fileUpload", this.getModel());
        fileUpload.getConfig().maxFileCount(10);
        fileUpload.getConfig().allowedFileTypes(Collections.singletonList(".csv"));
        fileUpload.getConfig().showPreview(false);
        fileUpload.getConfig().showUpload(false);

        form.add(fileUpload);

        LinkBuilderFactory.submitLink(uploadAction())
                .attach(form, "submit");

        this.add(form);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> uploadAction() {
        return (ajaxRequestTarget, components) -> {

            UploadCsvPanel parent = components.findParent(UploadCsvPanel.class);

            List<File> files = parent.getModelObject().stream()
                    .map(fileUpload -> {
                        File newFile;
                        try {
                            Path path =  Files.createDirectories(Paths.get(String.format("%s/%s", UPLOAD_FOLDER, parent.personModel.getObject().getUuid())));
                            newFile = new File(String.format("%s/%s", path.toString(), fileUpload.getClientFileName()));
                            fileUpload.writeTo(newFile);
                        } catch (Exception e) {
                            LOG.error(() -> String.format("Exception (%s) caught in uploadAction: %s", e.getClass().getName(), e.getMessage()), e);
                            throw new RuntimeException(e);
                        }
                        return newFile;
                    })
                    .collect(Collectors.toList());

            parent.csvFilesModel.setObject(parent.csvService.identifyCsvFiles(files, parent.personModel.getObject()));
            ajaxRequestTarget.add(parent.findPage());
        };
    }
}
