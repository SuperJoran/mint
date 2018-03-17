package be.superjoran.common.link;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.danekja.java.util.function.serializable.SerializableSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jorandeboever
 * Date: 24-Dec-16.
 */
public abstract class LinkBuilderSupport<L extends LinkBuilderSupport<L, F>, F extends AbstractLink> {

    private SerializableFunction<String, Component> iconProvider;
    private final List<SerializableFunction<F, ? extends Behavior>> behaviors = new ArrayList<>();
    private IModel<? extends String> bodyModel;
    private SerializableConsumer<F> configureConsumer;

    public LinkBuilderSupport() {
    }

    public L usingDefaults() {
        this.behave(() -> new AttributeAppender("class", "btn btn-default"));
        return (L) this.self();
    }

    public L small() {
        this.behave(() -> new AttributeAppender("class", " btn-sm"));
        return (L) this.self();
    }
    public L icon(SerializableFunction<String, Component> iconSupplier) {
        //TODO should do .andThen() -> Custom wicketconsumer?
        this.iconProvider = iconSupplier;
        return this.self();
    }

    public L behave(SerializableSupplier<? extends Behavior> behavior) {
        this.behaviors.add(components -> behavior.get());
        return this.self();
    }

    public L body(IModel<? extends String> bodyModel){
        this.bodyModel = bodyModel;
        return this.self();
    }

    public L configure(SerializableConsumer<F> configureConsumer) {
        this.configureConsumer = configureConsumer;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    private L self() {
        return (L) this;
    }

    abstract F buildLink(String id);

    public F build(String id){
        F link = this.buildLink(id);
        Optional.ofNullable(this.iconProvider).ifPresent(ip -> link.add(ip.apply("icon")));
        this.behaviors.forEach(f -> link.add(f.apply(link)));
        link.setOutputMarkupPlaceholderTag(true);
        Optional.ofNullable(this.bodyModel).ifPresent(link::setBody);
        Optional.ofNullable(this.configureConsumer).ifPresent(consumer -> consumer.accept(link));
        return link;
    }

    public L attach(MarkupContainer initialParent, String id) {
        initialParent.add(this.build(id));
        return this.self();
    }
}
