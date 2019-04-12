package ramonnteixeira.mywarrantymobile.model.repositories

import ramonnteixeira.mywarrantymobile.model.entity.Warranty

interface WarrantyRepository {

    fun findByState(expired: Boolean?): List<Warranty>
    fun create(warranty: Warranty)
}