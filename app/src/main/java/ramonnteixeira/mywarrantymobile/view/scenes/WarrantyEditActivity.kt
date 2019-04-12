package ramonnteixeira.mywarrantymobile.view.scenes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_warranty_edit.*
import kotlinx.android.synthetic.main.warranty_edit.*
import ramonnteixeira.mywarrantymobile.R
import ramonnteixeira.mywarrantymobile.controller.WarrantyController
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import java.time.LocalDate
import java.io.ByteArrayOutputStream


class WarrantyEditActivity : AppCompatActivity() {

    var controller: WarrantyController? = null
    private val requestProductPhoto = 1
    private val requestInvoicePhoto = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warranty_edit)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        controller = WarrantyController(applicationContext)

        saveButton.setOnClickListener{saveButtonOnclick()}
        productPhotoButton.setOnClickListener{dispatchTakePictureIntent(requestProductPhoto)}
        invoicePhotoButton.setOnClickListener{dispatchTakePictureIntent(requestInvoicePhoto)}
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun dispatchTakePictureIntent(resultType: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, resultType)
            }
        }
    }

    private fun saveButtonOnclick() {
        controller!!.create(
            Warranty("",
                productName.text.toString(),
                productPhoto.text.toString(),
                LocalDate.parse(acquisitionDate.text.toString()),
                LocalDate.parse(expireDate.text.toString()),
                invoicePhoto.text.toString()
            )
        )


        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }

        when (requestCode) {
            requestProductPhoto -> {
                val bitmap = data!!.extras.get("data") as Bitmap
                productPhoto.text = bitmapToBase64(bitmap)
                productPhotoButton.setImageBitmap(bitmap)
            }
            requestInvoicePhoto -> {
                val bitmap = data!!.extras.get("data") as Bitmap
                invoicePhoto.text = bitmapToBase64(bitmap)
                invoicePhotoButton.setImageBitmap(bitmap)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
