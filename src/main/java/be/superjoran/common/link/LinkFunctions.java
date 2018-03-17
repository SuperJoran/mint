package be.superjoran.common.link;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.BaseForm.FormMode;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

public class LinkFunctions {

    public static SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> read() {
        return ((ajaxRequestTarget, components) -> {
            BaseForm<?> form = components.findParent(BaseForm.class);
            IModel<FormMode> formModeModel = form.getFormModeModel();
            formModeModel.setObject(FormMode.READ);
        });
    }

    public static <T> SerializableBiConsumer<AjaxRequestTarget, AjaxLink<T>> edit() {
        return ((ajaxRequestTarget, components) -> {
            BaseForm<?> form = components.findParent(BaseForm.class);
            IModel<FormMode> formModeModel = form.getFormModeModel();
            formModeModel.setObject(FormMode.EDIT);
            ajaxRequestTarget.add(form);
        });
    }
}
