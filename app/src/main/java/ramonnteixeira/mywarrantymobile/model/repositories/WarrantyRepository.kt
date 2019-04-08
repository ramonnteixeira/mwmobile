package ramonnteixeira.mywarrantymobile.model.repositories

import ramonnteixeira.mywarrantymobile.model.entity.Warranty

interface WarrantyRepository {

    fun findAll(): List<Warranty>

}