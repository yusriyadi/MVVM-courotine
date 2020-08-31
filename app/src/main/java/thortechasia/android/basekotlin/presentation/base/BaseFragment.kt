package thortechasia.android.basekotlin.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.layout_action_bar.*
import org.jetbrains.anko.toast
import thortechasia.android.basekotlin.utils.MyDialog
import thortechasia.android.basekotlin.utils.gone

open class BaseFragment : Fragment() {
    lateinit var dialog: MyDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = MyDialog(requireContext())
    }

    fun setupToolbar(isVisible :Boolean=true, title : String){
        if(isVisible){
            toolbar?.title = title
            toolbar?.setNavigationOnClickListener {
                findNavController().popBackStack()
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

    fun toast(msg : String){
        requireContext().toast(msg)
    }

}