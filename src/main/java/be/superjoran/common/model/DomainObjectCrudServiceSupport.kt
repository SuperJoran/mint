package be.superjoran.common.model

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */

import be.superjoran.mint.domain.DomainObject
import com.google.common.collect.Lists
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validator
import kotlin.collections.HashSet

abstract class DomainObjectCrudServiceSupport<T : DomainObject> : DomainObjectCrudService<T> {

    protected abstract val dao: CrudRepository<T, String>
    protected abstract val validator: Validator

    override fun findOne(id: String): Optional<T> {
        return this.dao.findById(id)
    }

    override fun findAll(): List<T> {
        return Lists.newArrayList(this.dao.findAll())
    }

    override fun delete(`object`: T) {
        this.dao.delete(`object`)
    }

    override fun save(`object`: T): T {
        return this.dao.save(`object`)
    }

    override fun save(objects: Iterable<T>): Iterable<T> {
        val collection: MutableSet<ConstraintViolation<T>> = HashSet()
        objects.forEach({ o -> collection.addAll(this.validator.validate(o)) })
        if (collection.isNotEmpty()) {
            throw ConstraintViolationException(collection)
        }

        return this.dao.saveAll(objects)
    }
}
