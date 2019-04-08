package ramonnteixeira.mywarrantymobile.model.entity

import java.time.LocalDate

class Warranty(
    val _id: String,
    val productName: String,
    val productPhoto: String,
    val aquisitionDate: LocalDate,
    val expirationDate: LocalDate,
    val invoicePhoto: String
)