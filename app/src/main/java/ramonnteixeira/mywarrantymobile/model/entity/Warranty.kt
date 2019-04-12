package ramonnteixeira.mywarrantymobile.model.entity

import java.time.LocalDate

class Warranty(
    var _id: String,
    val productName: String,
    val productPhoto: String,
    val acquisitionDate: LocalDate,
    val expireDate: LocalDate,
    val invoicePhoto: String
)