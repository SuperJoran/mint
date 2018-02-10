package be.superjoran.common.datatable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

/**
 * Created by Jorandeboever
 * Date: 29-Dec-16.
 */
class ActiesColumn<T, S> extends CustomColumn<T, S> {

    ActiesColumn(IModel<String> displayModel, SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> editConsumer, SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> deleteConsumer ) {
        super(displayModel,  (s, model) -> new EditDeleteCell<T>(s, model, editConsumer, deleteConsumer));
    }
}
