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

    override val id: String?
        get() = this.uuid
    override val displayValue: String?
        get() = this.uuid

}
