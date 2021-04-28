package tellabs.android.basekotlin.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tellabs.android.basekotlin.data.db.entity.Sale
import tellabs.android.basekotlin.data.db.entity.SaleItemDetail
import tellabs.android.basekotlin.data.db.entity.SaleLiveData
import kotlin.random.Random


class CashierFragmentViewModel() : ViewModel() {
    var _sale = Sale()
    private var saleLiveData = MutableLiveData<Sale>()
    private val list = mutableListOf<SaleItemDetail>()

    init {
        generateSaleItemDummy()
    }
    fun getSaleLiveData(): MutableLiveData<Sale> {
        if (saleLiveData.value==null){
            saleLiveData.value = Sale()
        }
        return saleLiveData
    }
    fun updateSaleLiveData(sale: Sale) {
        saleLiveData.value = sale
    }


    fun getDummySaleItemList() = MutableLiveData(list)

    fun generateSaleItemDummy(){
        val listName = listOf("bakso", "soto", "miayam", "es jeruk", "nasi goreng", "pecel", "roti bakar")
        val listHarga = listOf(2000.0, 15000.0, 55000.0)
            listName.forEach {
                list.add(SaleItemDetail(namaItem = it, price = listHarga.random(),balance = 20, qty = 0))
            }
        getDummySaleItemList().value = list
    }

    var listItemDetail = MutableLiveData(SaleItemDetail())
    fun addToReceipt(saleItemDetail: SaleItemDetail){
        listItemDetail.value = saleItemDetail
    }

}


