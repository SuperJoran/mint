package be.superjoran.mint.domain

import java.util.*
import javax.persistence.*

/**
 * Created by Jorandeboever
 * Date: 30-Apr-17.
 */
@Entity
@Table(name = "T_CATEGORY_GROUP")
class CategoryGroup(
        var name: String,
        @Column(nullable = false, name = "CATEGORY_TYPE")
        @Enumerated(EnumType.STRING)
        var categoryType: CategoryType
) : DomainObject() {


    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    private val categoryList: List<Category> = ArrayList()

    fun getCategoryList(): List<Category> {
        return this.categoryList
    }

    override val displayValue: String?
        get() = this.name

}
