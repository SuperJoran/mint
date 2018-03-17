package be.superjoran.mint.domain

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@MappedSuperclass
abstract class DomainObject : Serializable, Cloneable, Display {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var uuid: String? = null

    constructor()

    constructor(uuid: String) {
        this.uuid = uuid
    }

    override fun equals(other: Any?): Boolean {
        if (other is DomainObject) {
            return other.uuid == this.uuid
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return this.uuid?.hashCode() ?: super.hashCode()
    }

    override val id: String?
        get() = this.uuid
    override val displayValue: String?
        get() = this.toString()

}
