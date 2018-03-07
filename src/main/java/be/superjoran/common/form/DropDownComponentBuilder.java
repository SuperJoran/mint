package be.superjoran.common.form;

import be.superjoran.mint.domain.Display;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelect;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jorandeboever
 * Date: 05-Feb-17.
 */
public class DropDownComponentBuilder<T extends Display & Serializable> extends FormComponentBuilder<DropDownChoice<T>, T, DropDownComponentBuilder<T>> {

    private IModel<List<T>> listModel;

    @Override
    public DropDownComponentBuilder<T> attach(MarkupContainer initialParent, String id, IModel<T> model) {
        throw new IllegalArgumentException();
    }

    public DropDownComponentBuilder<T> attach(MarkupContainer initialParent, String id, IModel<T> model, IModel<List<T>> listIModel) {
        this.listModel = listIModel::getObject;
        super.attach(initialParent, id, model);

        return this.self();
    }

    @Override
    DropDownChoice<T> buildFormComponent(String id, IModel<T> model) {
        BootstrapSelect<T> dropdown = new BootstrapSelect<>(
                id,
                model,
                this.listModel,
                new CustomChoiceRenderer()
        );
        dropdown.setNullValid(true);
        return dropdown;
    }
}
