package be.superjoran.common.link;

import be.superjoran.common.form.BaseForm;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;

/**
 * Created by Jorandeboever
 * Date: 24-Dec-16.
 */
public class AjaxSubmitLinkBuilder extends AjaxLinkBuilderSupport<AjaxSubmitLinkBuilder, AjaxSubmitLink> {
     AjaxSubmitLinkBuilder(SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> onClickConsumer) {
        super(onClickConsumer);
    }


    @Override
    AjaxSubmitLink buildLink(String id, SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> onClickConsumer) {
        return new MySubmitLink(id, onClickConsumer);
    }

    private static class MySubmitLink extends AjaxSubmitLink {
        private final SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> submitConsumer;

        private MySubmitLink(String id, SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> submitConsumer) {
            super(id);
            this.submitConsumer = submitConsumer;
        }

        @Override
        protected void onError(AjaxRequestTarget target) {
            super.onError(target);
            target.add(this.findParent(BaseForm.class));
        }

        @Override
        protected void onAfterSubmit(AjaxRequestTarget target) {
            super.onAfterSubmit(target);
            this.submitConsumer.accept(target, this);
            BaseForm<?> parent = this.findParent(BaseForm.class);
            target.add(parent);
        }
    }
}
