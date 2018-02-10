package be.superjoran.common.form;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.IModel;

/**
 * Created by Jorandeboever
 * Date: 25-Dec-16.
 */
public class NumberTextFieldComponentBuilder<N extends Number & Comparable<N>> extends FormComponentBuilder<NumberTextField<N>, N, NumberTextFieldComponentBuilder<N>> {
    NumberTextFieldComponentBuilder() {
    }

    @Override
    NumberTextField<N> buildFormComponent(String id, IModel<N> model) {
        return new NumberTextField<>(id, model);
    }
}
