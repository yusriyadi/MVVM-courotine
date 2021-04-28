package tellabs.android.basekotlin.presentation.listitem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.d
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_item_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.data.db.entity.Sale
import tellabs.android.basekotlin.data.db.entity.SaleItemDetail
import tellabs.android.basekotlin.presentation.main.CashierFragmentViewModel
import tellabs.android.basekotlin.presentation.purchase.ReceiptAdapter
import tellabs.android.basekotlin.utils.objectToJson

class ListItemFragment:Fragment(R.layout.fragment_item_fragment){
    private val vm : CashierFragmentViewModel by activityViewModels()

    lateinit var adapterx: ReceiptAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterx = ReceiptAdapter(mutableListOf(),false){data->
            Log.d(this::class.java.simpleName, "onViewCreated: "+ objectToJson(data))
            vm.addToReceipt(data)
            vm.getSaleLiveData().apply {
                this.value?.let {
                    it.total +=data.price
                    it.saleItem.add(data)
                    value = it
                }
            }
        }

        recyclerView2.apply {
            adapter = adapterx
            layoutManager = LinearLayoutManager(requireContext())
        }

        vm.getDummySaleItemList().observe(requireActivity(), Observer {
            adapterx.datas.addAll(it)
            adapterx.notifyDataSetChanged()
        })


        btnSimpan.setOnClickListener {

        }

        btnClear.setOnClickListener {
            vm.getSaleLiveData().value = Sale()
            vm.listItemDetail.value = SaleItemDetail()
        }

    }

}