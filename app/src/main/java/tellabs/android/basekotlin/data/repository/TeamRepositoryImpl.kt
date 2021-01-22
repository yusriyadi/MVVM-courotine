package tellabs.android.basekotlin.data.repository

import androidx.lifecycle.LiveData
import tellabs.android.basekotlin.data.db.dao.TeamDao
import tellabs.android.basekotlin.data.db.entity.TeamEntity
import tellabs.android.basekotlin.data.remote.response.TeamResponse
import tellabs.android.basekotlin.data.remote.service.TeamService
import tellabs.android.basekotlin.domain.Team
import tellabs.android.basekotlin.utils.mapToListDomain

class TeamRepositoryImpl(val teamService: TeamService,
                         val teamDao: TeamDao) : TeamRepository{

    override suspend fun getTeams(league: String): List<Team> {
        return mapToListDomain(teamService.getAllTeams(league).teams)
    }

    //the way without maping
    override suspend fun getTeamsNonMap(league: String): TeamResponse {
        return teamService.getAllTeams(league)
    }

    override suspend fun getFavList(): List<TeamEntity> {
        return teamDao.findAll()
    }

    override suspend fun checkFavTeam(teamName: String): List<TeamEntity> {
        return teamDao.checkFavTeam(teamName)
    }


    override suspend fun addFav(data: TeamEntity) {
        teamDao.insert(data)
    }

    override suspend fun deletFav(data: TeamEntity) {
        teamDao.delete(data)
    }

    override fun getListLiveData(): LiveData<List<TeamEntity>> {
        return teamDao.getListLiveData()
    }


}