package be.superjoran.common;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.BaseForm.FormMode;
import org.apache.wicket.Component;

public class FormModeVisibilityBehavior extends VisibilityBehaviorSupport {

    private final BaseForm.FormMode formMode;

    public FormModeVisibilityBehavior(FormMode formMode) {
        this.formMode = formMode;
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        boolean visibilityAllowed = this.formMode == component.findParent(BaseForm.class).getFormModeModel().getObject();
        component.setVisibilityAllowed(visibilityAllowed);
    }
}
