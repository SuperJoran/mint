package be.superjoran.common.model;

import be.superjoran.mint.domain.DomainObject;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jorandeboever
 * Date: 27-Dec-16.
 */
public class DomainObjectListModel<T extends DomainObject, S extends DomainObjectCrudService<T>> extends LoadableListModel<T> {
    private static final long serialVersionUID = -8362218176857848881L;
    private final SerializableFunction<S, List<T>> loadFunction;

    private final S service;

    public DomainObjectListModel(S service) {
        this(service, DomainObjectCrudService::findAll);
    }

    public DomainObjectListModel(S service, SerializableFunction<S, List<T>> loadFunction) {
        this.service = service;
        this.loadFunction = loadFunction;
    }

    @Override
    protected List<T> load() {
        return this.loadFunction.apply(this.service);
    }
}
