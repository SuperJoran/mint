package be.superjoran.common;

import org.apache.wicket.Component;
import org.danekja.java.util.function.serializable.SerializableFunction;

public class IModelVisibilityBehavior<X extends Component> extends VisibilityBehavior<X> {


    public IModelVisibilityBehavior(SerializableFunction<? super X, Boolean> visibilityLogic) {
        super(visibilityLogic);
    }
}
