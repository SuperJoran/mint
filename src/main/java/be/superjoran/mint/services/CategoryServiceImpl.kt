package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudServiceSupport
import be.superjoran.mint.dao.CategoryDao
import be.superjoran.mint.domain.Category
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(override val dao: CategoryDao) : DomainObjectCrudServiceSupport<Category>(), CategoryService
