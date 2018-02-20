package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudService;
import be.superjoran.mint.domain.Category;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;

import java.util.List;

public interface StatementService extends DomainObjectCrudService<Statement> {

    List<Statement> findAllByOwner(Person owner);

    void assignCategories(List<Statement> statements, Category category);
}
