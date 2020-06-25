package thortechasia.android.basekotlin.presentation.main

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_row_team.view.*
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.loadImageFromUrl
import java.util.*

class TeamItemAdapter(val team: Team, val listener : OnClickListerner) : Item(){

    interface OnClickListerner{
        fun onClick(team :Team)
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            //load image with extention function
            itemView.imgTeam.loadImageFromUrl(team.teamLogo)

            itemView.txtTeam.text = team.teamName

            itemView.setOnClickListener {
                listener.onClick(team)
            }
        }

    }

    override fun getLayout(): Int = R.layout.item_row_team

}