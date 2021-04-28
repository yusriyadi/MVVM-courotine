package tellabs.android.basekotlin.presentation.purchase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row_receipt.view.*
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.data.db.entity.Sale
import tellabs.android.basekotlin.data.db.entity.SaleItemDetail
import tellabs.android.basekotlin.utils.gone
import tellabs.android.basekotlin.utils.visible

class ReceiptAdapter(val datas : MutableList<SaleItemDetail>, val fromReceipt : Boolean, val onClick: (SaleItemDetail)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_receipt, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            if (fromReceipt){
                tvQty.visible()
            }else{
                tvQty.gone()
                holder.itemView.setOnClickListener {
                 onClick(datas[position])
                }
            }

            datas[position].let {
                tvItemName.text = it.namaItem
                tvPrice.text = "Rp. "+it.price.toString()
                tvQty.text = "x"+it.qty.toString()
            }
        }


    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
