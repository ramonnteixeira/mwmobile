package ramonnteixeira.mywarrantymobile.view.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_warranty.view.*
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import java.time.Duration
import java.time.LocalDate
import android.graphics.BitmapFactory
import android.util.Base64
import ramonnteixeira.mywarrantymobile.R


class ItemWarrantyAdapter(
    private var warranties: MutableList<Warranty>
): RecyclerView.Adapter<ItemWarrantyAdapter.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warranty, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val warranty = warranties[position]
        if (warranty.expireDate.isBefore(LocalDate.now())) {
            holder.itemView.productName.setTextColor(Color.GRAY)
            holder.itemView.productName.paintFlags = holder.itemView.productName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemView.productName.setTextColor(Color.BLACK)
            holder.itemView.productName.paintFlags = holder.itemView.productName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        holder.bind(warranty)
    }

    override fun getItemCount(): Int {
        return warranties.size
    }

    class ViewHolder(@NonNull itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(warranty: Warranty) {
            itemView.productName.text = warranty.productName
            val warrantyDays = Duration.between(LocalDate.now().atStartOfDay(), warranty.expireDate.atStartOfDay()).toDays().toString()
            itemView.warrantyDays.text = context.getString(R.string.warranty_days_format).format(warrantyDays)

            if (warranty.productPhoto.length > 1) {
                val decodedString = Base64.decode(warranty.productPhoto, Base64.DEFAULT)
                val productBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                itemView.productPhoto.setImageBitmap(productBitmap)
            }
        }
    }

}