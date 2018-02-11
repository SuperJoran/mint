package be.superjoran.mint.domain

import java.util.*
import javax.persistence.*

/**
 * Created by Jorandeboever
 * Date: 30-Apr-17.
 */
@Entity
@Table(name = "T_CATEGORY_GROUP")
class CategoryGroup : DomainObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "ADMINISTRATOR_UUID")
    var administrator: Person? = null

    var name: String? = null


    @Column(nullable = false, name = "CATEGORY_TYPE")
    @Enumerated(EnumType.STRING)
    var categoryType: CategoryType? = null


    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    private val categoryList: List<Category> = ArrayList()

    constructor() {}

    constructor(administrator: Person) {
        this.administrator = administrator
    }

    constructor(name: String) {
        this.name = name
    }

    fun getCategoryList(): List<Category> {
        return this.categoryList
    }

    override fun getDisplayValue(): String? {
        return this.name
    }

    override fun toString(): String {
        return this.name.toString()
    }

    companion object {
        private val serialVersionUID = 6772160125295115979L
    }
}
