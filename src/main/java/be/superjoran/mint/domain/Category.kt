package be.superjoran.mint.domain

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_CATEGORY")
class Category(
        uuid: String,
        var name: String,
        @ManyToOne(optional = false)
        @JoinColumn(name = "CATEGORY_GROUP_UUID")
        var categoryGroup: CategoryGroup
) : DomainObject(uuid) {

    override fun toString(): String {
        return String.format("%s - %s", this.categoryGroup.name, this.name)
    }

}
