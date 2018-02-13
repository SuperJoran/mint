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
class Category : DomainObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORY_GROUP_UUID")
    var categoryGroup: CategoryGroup? = null

    var name: String? = null

    constructor()

    constructor(uuid: String) : super(uuid)

    override fun getDisplayValue(): String {
        return this.toString()
    }

    override fun toString(): String {
        return String.format("%s - %s", this.categoryGroup!!.name, this.name)
    }

    companion object {
        private val serialVersionUID = -163498981511624588L
    }
}
