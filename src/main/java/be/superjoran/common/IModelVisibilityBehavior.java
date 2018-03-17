package be.superjoran.common;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializablePredicate;

public class IModelVisibilityBehavior<X> extends VisibilityBehaviorSupport {

    private final IModel<X> model;
    private final SerializablePredicate<X> visiblePredicate;

    public IModelVisibilityBehavior(IModel<X> model, SerializablePredicate<X> visiblePredicate) {
        this.model = model;
        this.visiblePredicate = visiblePredicate;
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);
        component.setVisibilityAllowed(this.visiblePredicate.test(this.model.getObject()));
    }
}
