package ramonnteixeira.mywarrantymobile.model.repositories.jdbc

import android.content.ContentValues
import android.content.Context
import com.example.todounibavekotlin.model.Database
import com.example.todounibavekotlin.model.DatabaseConstants
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import ramonnteixeira.mywarrantymobile.model.repositories.WarrantyRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WarrantyJdbcRepository(context: Context): WarrantyRepository {

    private val dbConst = DatabaseConstants()
    private val database: Database = Database(context)

    override fun findByState(expired: Boolean?): List<Warranty> {

        val columns = arrayOf(
            dbConst.columnId,
            dbConst.columnProductName,
            dbConst.columnProductPhoto,
            dbConst.columnAcquisitionDate,
            dbConst.columnExpirationDate,
            dbConst.columnInvoicePhoto
        )

        var where: String? = null
        if (expired  != null) {
            val signal = if (expired!!) "<" else ">="
            where = "${dbConst.columnExpirationDate} $signal date('now')"
        }

        val cursor = database.readableDatabase.query(
            dbConst.tableWarranty,
            columns,
            where,
            null,
            null,
            null,
            "${dbConst.columnExpirationDate}")

        val warranties = mutableListOf<Warranty>()
        if (cursor.count == 0) {
            return warranties
        }

        cursor.moveToFirst()
        do {
            val id = cursor.getString(cursor.getColumnIndex(dbConst.columnId))
            val productName = cursor.getString(cursor.getColumnIndex(dbConst.columnProductName))
            val productPhoto = cursor.getString(cursor.getColumnIndex(dbConst.columnProductPhoto))
            val acquisitionDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(dbConst.columnAcquisitionDate)))
            val expirationDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(dbConst.columnExpirationDate)))
            val invoicePhoto = cursor.getString(cursor.getColumnIndex(dbConst.columnInvoicePhoto))

            warranties.add(Warranty(id, productName, productPhoto, acquisitionDate, expirationDate, invoicePhoto))
        } while (cursor.moveToNext())

        return warranties
    }

    override fun create(warranty: Warranty) {
        val content = ContentValues()
        content.put(dbConst.columnId, warranty._id)
        content.put(dbConst.columnProductName, warranty.productName)
        content.put(dbConst.columnProductPhoto, warranty.productPhoto)
        content.put(dbConst.columnInvoicePhoto, warranty.invoicePhoto)
        content.put(dbConst.columnAcquisitionDate, warranty.acquisitionDate.format(DateTimeFormatter.ISO_DATE))
        content.put(dbConst.columnExpirationDate, warranty.expireDate.format(DateTimeFormatter.ISO_DATE))

        val db = database.writableDatabase
        db.insert(dbConst.tableWarranty, null, content)
        db.close()
    }
}