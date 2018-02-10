package be.superjoran.common.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by Jorandeboever
 * Date: 23-Dec-16.
 */
public class BaseForm<T> extends Form<T> {
    private static final long serialVersionUID = 227213741181309036L;

    private final IModel<FormMode> formModeModel;

    public BaseForm(String id, IModel<T> model) {
        super(id, model);
        this.formModeModel = new Model<>(FormMode.EDIT);
    }

    public IModel<FormMode> getFormModeModel() {
        return this.formModeModel;
    }

    @Override
    public void process(IFormSubmitter submittingComponent) {
        super.process(submittingComponent);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);
    }

    public enum  FormMode {
        READ, EDIT
    }
}
