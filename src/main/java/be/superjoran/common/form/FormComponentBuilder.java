package be.superjoran.common.form;

import be.superjoran.common.VisibilityBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.danekja.java.util.function.serializable.SerializableSupplier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Jorandeboever
 * Date: 23-Dec-16.
 */
public abstract class FormComponentBuilder<X extends FormComponent<?>, T extends Serializable, F extends FormComponentBuilder<X, T, F>> {

    private SerializableBiFunction<String, IModel<String>, Component> labelSupplier = Label::new;
    private boolean switchable = true;
    private SerializableSupplier<IModel<String>> labelModel = Model::new;
    private boolean required = false;
    private boolean usingLabel = true;
    private final Collection<SerializableSupplier<? extends Behavior>> behaviors = new ArrayList<>();
    private SerializableFunction<String, MarkupContainer> containerSupplier;
    private SerializableConsumer<X> configureConsumer = c -> {};

    public F usingDefaults() {
        this.switchable = true;
        return this.self();
    }

    public F switchable(boolean switchable) {
        this.switchable = switchable;
        return this.self();
    }

    public F noLabel() {
        this.usingLabel = false;
        return this.self();
    }

    public F configure(SerializableConsumer<X> configureConsumer) {
        this.configureConsumer = configureConsumer;
        return this.self();
    }

    public F notRequired() {
        this.required = false;
        return this.self();
    }

    public F container() {
        this.containerSupplier = WebMarkupContainer::new;
        return this.self();
    }

    public F container(SerializableConsumer<MarkupContainer> containerConsumer) {
        if(this.containerSupplier == null){
            this.container();
        }
        this.containerSupplier = this.containerSupplier.andThen(components -> {
            containerConsumer.accept(components);
            return components;
        });
        return this.self();
    }


    public F required() {
        this.required = true;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    protected F self() {
        return (F) this;
    }

    public F behave(SerializableSupplier<? extends Behavior> behaviorFunction) {
        this.behaviors.add(behaviorFunction);
        return this.self();
    }

    abstract X buildFormComponent(String id, IModel<T> model);

    public F attach(MarkupContainer initialParent, String id, IModel<T> model) {
        MarkupContainer parent = Optional.ofNullable(this.containerSupplier).map(supplier -> {
            MarkupContainer newParent = supplier.apply(id + "-container");
            newParent.setOutputMarkupId(true);
            initialParent.add(newParent);
            return newParent;
        }).orElse(initialParent);
        if(this.usingLabel) {
            Component label = this.labelSupplier.apply(id + "-label", this.labelModel.get());
            parent.add(label.setOutputMarkupPlaceholderTag(true));
        }
        X formComponent = this.buildFormComponent(id, model);
        formComponent.setOutputMarkupId(true);

        formComponent.setRequired(this.required);
        this.configureConsumer.accept(formComponent);

        this.behaviors.forEach(wicketFunction -> formComponent.add(wicketFunction.get()));

        if (this.switchable) {
            Component readLabel = new Label(id + "-read", model);
            parent.add(readLabel.setOutputMarkupPlaceholderTag(true));
            formComponent.add(new VisibilityBehavior<>(component -> component.findParent(BaseForm.class).getFormModeModel().getObject().equals(BaseForm.FormMode.EDIT)));
            readLabel.add(new VisibilityBehavior<>(component -> component.findParent(BaseForm.class).getFormModeModel().getObject().equals(BaseForm.FormMode.READ)));
        }
        parent.add(formComponent.setOutputMarkupPlaceholderTag(true));
        return this.self();
    }

    public F body(ResourceModel labelModel) {
        this.labelModel = () -> labelModel;
        return this.self();
    }
}
