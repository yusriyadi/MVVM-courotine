package thortechasia.android.basekotlin.data.repository

import androidx.lifecycle.LiveData
import io.reactivex.Single
import thortechasia.android.basekotlin.data.db.dao.TeamDao
import thortechasia.android.basekotlin.data.db.entity.TeamEntity
import thortechasia.android.basekotlin.data.remote.response.TeamResponse
import thortechasia.android.basekotlin.data.remote.service.TeamService
import thortechasia.android.basekotlin.domain.Team
import thortechasia.android.basekotlin.utils.mapToListDomain

class TeamRepositoryImpl(val teamService: TeamService,
                         val teamDao: TeamDao) : TeamRepository{

    override suspend fun getTeams(league: String): List<Team> {
        return mapToListDomain(teamService.getAllTeams(league).teams)
    }

    //the way without maping
    override suspend fun getTeamsNonMap(league: String): TeamResponse {
        return teamService.getAllTeams(league)
    }

    override suspend fun getFavList(): LiveData<List<TeamEntity>> {
        return teamDao.findAll()
    }

    override suspend fun addFav(data: TeamEntity) {
        teamDao.insert(data)
    }


}