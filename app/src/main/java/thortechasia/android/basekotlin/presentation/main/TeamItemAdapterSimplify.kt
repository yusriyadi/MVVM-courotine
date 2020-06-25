package thortechasia.android.basekotlin.presentation.main

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_row_team.view.*
import thortechasia.android.basekotlin.R
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.loadImageFromUrl
import java.util.*

class TeamItemAdapterSimplify(val team: Team, val listener : (Team)->Unit) : Item(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            itemView.imgTeam.loadImageFromUrl(team.teamLogo)
            itemView.txtTeam.text = team.teamName

            itemView.setOnClickListener {
                listener(team)
            }
        }

    }

    override fun getLayout(): Int = R.layout.item_row_team

}