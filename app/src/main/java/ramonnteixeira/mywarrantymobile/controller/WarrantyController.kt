package ramonnteixeira.mywarrantymobile.controller

import android.content.Context
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import ramonnteixeira.mywarrantymobile.model.repositories.WarrantyRepository
import ramonnteixeira.mywarrantymobile.model.repositories.jdbc.WarrantyJdbcRepository

class WarrantyController(
    context: Context?,
    private val repository: WarrantyRepository = WarrantyJdbcRepository(context!!)
    ){

    fun findAll(): List<Warranty> {
        return repository.findAll()
    }

    fun findActives(): List<Warranty> {
        return repository.findAll()
    }

    fun findExpireds(): List<Warranty> {
        return repository.findAll()
    }

}