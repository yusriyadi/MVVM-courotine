package tellabs.android.basekotlin.presentation.main

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_row_team.view.*
import tellabs.android.basekotlin.R
import tellabs.android.basekotlin.domain.Team
import tellabs.android.basekotlin.utils.loadImageFromUrl

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