package ramonnteixeira.mywarrantymobile.controller

import android.content.Context
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import ramonnteixeira.mywarrantymobile.model.repositories.WarrantyRepository
import ramonnteixeira.mywarrantymobile.model.repositories.jdbc.WarrantyJdbcRepository
import java.util.*

class WarrantyController(
    context: Context?,
    private val repository: WarrantyRepository = WarrantyJdbcRepository(context!!)
    ){

    fun findAll(): List<Warranty> {
        return repository.findByState(null)
    }

    fun findActives(): List<Warranty> {
        return repository.findByState(false)
    }

    fun findExpired(): List<Warranty> {
        return repository.findByState(true)
    }

    fun create(warranty: Warranty) {
        warranty._id = UUID.randomUUID().toString()
        repository.create(warranty)
    }
}