package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.CategoryDao;
import be.superjoran.mint.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends DomainObjectCrudServiceSupport<Category> implements CategoryService {
    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    protected CrudRepository<Category, String> getDao() {
        return this.categoryDao;
    }
}
