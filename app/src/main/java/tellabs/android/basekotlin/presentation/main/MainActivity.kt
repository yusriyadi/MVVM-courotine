package tellabs.android.basekotlin.presentation.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.presentation.listitem.ListItemFragment
import tellabs.android.basekotlin.presentation.purchase.ReceiptFragment

class MainActivity : AppCompatActivity() {
    var TAG = this::class.java.simpleName
    private val vm :CashierFragmentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.commit {
            add(R.id.frameLeft, ReceiptFragment(), "")
            add(R.id.frameRight, ListItemFragment(), "")
        }


    }


}