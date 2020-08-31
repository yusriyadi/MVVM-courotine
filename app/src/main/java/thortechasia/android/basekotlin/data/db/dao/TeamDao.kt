package thortechasia.android.basekotlin.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import thortechasia.android.basekotlin.data.db.entity.TeamEntity

@Dao
interface TeamDao : BaseDao<TeamEntity> {

    @Query("SELECT * FROM team")
    suspend fun findAll() : List<TeamEntity>

    @Query("SELECT * FROM team WHERE teamName LIKE :teamName")
    suspend fun checkFavTeam(teamName : String) : List<TeamEntity>

}