package tellabs.android.basekotlin.data.db.entity

import androidx.lifecycle.LiveData

class SaleLiveData : LiveData<Sale>() {
    val a = {data : Sale->
        value = data
    }
}