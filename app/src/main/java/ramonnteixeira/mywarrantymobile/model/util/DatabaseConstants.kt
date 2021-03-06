package com.example.todounibavekotlin.model

class DatabaseConstants(
    val dbName: String = "mwmobile",
    val dbVersion: Int = 1,


    val tableWarranty: String = "WARRANTY",
    val columnId: String = "_id",
    val columnProductName: String = "product_name",
    val columnProductPhoto: String = "product_photo",
    val columnAcquisitionDate: String = "acquisition_date",
    val columnExpirationDate: String = "expire_date",
    val columnInvoicePhoto: String = "invoice_photo"

)
