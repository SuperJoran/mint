package be.superjoran.common.datatable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import be.superjoran.common.link.LinkBuilderFactory;

/**
 * Created by Jorandeboever
 * Date: 29-Dec-16.
 */
class EditDeleteCell<T> extends GenericPanel<T> {
    private final SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> editConsumer;
    private final SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> deleteConsumer;

    EditDeleteCell(String id, IModel<T> model, SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> editConsumer, SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> deleteConsumer) {
        super(id, model);
        this.editConsumer = editConsumer;
        this.deleteConsumer = deleteConsumer;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        LinkBuilderFactory.editLink(this.editConsumer)
                .usingDefaults()
                .small()
                .attach(this, "edit", this.getModel());

        LinkBuilderFactory.deleteLink(this.deleteConsumer)
                .usingDefaults()
                .small()
                .attach(this, "delete", this.getModel());

    }
}
