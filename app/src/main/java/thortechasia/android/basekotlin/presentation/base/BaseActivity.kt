package thortechasia.android.basekotlin.presentation.base

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.layout_action_bar.*
import org.jetbrains.anko.toast
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.utils.MyDialog
import thortechasia.android.basekotlin.utils.gone

open class BaseActivity : AppCompatActivity() {

    lateinit var dialog: MyDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = MyDialog(this)
    }


    fun setupToolbar(isVisible :Boolean=true, title : String , isBackVisble : Boolean=true){
        if(isVisible){
            toolbar?.title = title
            if(isBackVisble){
                setSupportActionBar(toolbar)
                toolbar?.setNavigationIcon(R.drawable.ic_round_arrow_back_ios_24)
                toolbar?.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
        }
        else{
            toolbar?.gone()
        }

    }


    fun loadingStart(){
        dialog.show()
    }

    fun loadingDismiss()
    {
        dialog.dismiss()
    }


}