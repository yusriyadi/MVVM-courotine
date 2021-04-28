package tellabs.android.basekotlin.presentation.purchase

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
import kotlinx.android.synthetic.main.fragment_receipt.*
import kotlinx.android.synthetic.main.fragment_receipt.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.data.db.entity.SaleItemDetail
import tellabs.android.basekotlin.presentation.main.CashierFragmentViewModel
import tellabs.android.basekotlin.utils.objectToJson

class ReceiptFragment : Fragment(R.layout.fragment_receipt) {
    private val vm : CashierFragmentViewModel by activityViewModels()
    lateinit var adapterx: ReceiptAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterx = ReceiptAdapter(mutableListOf(),true){

        }
        vm.listItemDetail.observe(requireActivity(), Observer {data->
                adapterx.datas.add(data)
                adapterx.notifyDataSetChanged()
        })
        vm.getSaleLiveData().observe(requireActivity(), Observer {
            Log.d("ReceiptFragment", " onCreate: "+Gson().toJson(it))
            textView5.text = it.total.toString()
        })



        recyclerView.apply {
            adapter = adapterx
            layoutManager = LinearLayoutManager(requireContext())
        }

    }






}