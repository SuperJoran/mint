package be.superjoran.common.form;

import org.apache.wicket.model.IModel;

import java.time.LocalDate;

/**
 * Created by Jorandeboever
 * Date: 25-Dec-16.
 */
public class LocalDateTextFieldComponentBuilder  extends FormComponentBuilder<LocalDateTextField, LocalDate, LocalDateTextFieldComponentBuilder> {
    LocalDateTextFieldComponentBuilder() {
    }

    @Override
    LocalDateTextField buildFormComponent(String id, IModel<LocalDate> model) {
        return new LocalDateTextField(id, model);
    }
}
