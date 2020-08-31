package thortechasia.android.basekotlin.data.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.data.remote.response.TeamResponse
import thortechasia.android.basekotlin.domain.Team

interface TeamRepository {

    suspend fun getTeams(league: String) : List<Team>
    //use this code if you need a whole response
    suspend fun getTeamsNonMap(league: String): TeamResponse
    suspend fun getFavList():List<TeamEntity>
    suspend fun checkFavTeam(teamName : String) :List<TeamEntity>
    suspend fun addFav(data: TeamEntity)
    suspend fun deletFav(data: TeamEntity)
}