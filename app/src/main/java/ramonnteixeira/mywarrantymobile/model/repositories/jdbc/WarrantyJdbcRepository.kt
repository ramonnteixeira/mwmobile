package ramonnteixeira.mywarrantymobile.model.repositories.jdbc

import android.content.Context
import com.example.todounibavekotlin.model.Database
import com.example.todounibavekotlin.model.DatabaseConstants
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import ramonnteixeira.mywarrantymobile.model.repositories.WarrantyRepository
import java.time.LocalDate

class WarrantyJdbcRepository(context: Context): WarrantyRepository {

    private val dbConst = DatabaseConstants()
    private val database: Database = Database(context)

    override fun findAll(): List<Warranty> {

        val columns = arrayOf(
            dbConst.columnId,
            dbConst.columnProductName,
            dbConst.columnProductPhoto,
            dbConst.columnAcquisitionDate,
            dbConst.columnExpirationDate,
            dbConst.columnInvoicePhoto
        )

        val cursor = database.readableDatabase.query(
            dbConst.tableWarranty,
            columns,
            null,
            null,
            null,
            null,
            "${dbConst.columnExpirationDate}")

        val warranties = mutableListOf<Warranty>()
        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(dbConst.columnId))
            val productName = cursor.getString(cursor.getColumnIndex(dbConst.columnProductName))
            val productPhoto = cursor.getString(cursor.getColumnIndex(dbConst.columnProductPhoto))
            val acquisitionDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(dbConst.columnAcquisitionDate)))
            val expirationDate = LocalDate.parse(cursor.getString(cursor.getColumnIndex(dbConst.columnExpirationDate)))
            val invoicePhoto = cursor.getString(cursor.getColumnIndex(dbConst.columnInvoicePhoto))

            warranties.add(Warranty(id, productName, productPhoto, acquisitionDate, expirationDate, invoicePhoto))
        }

        return warranties
    }

}