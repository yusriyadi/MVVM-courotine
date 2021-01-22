package tellabs.android.basekotlin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tellabs.android.basekotlin.data.db.dao.TeamDao
import tellabs.android.basekotlin.data.db.entity.TeamEntity

@Database(
    entities = [TeamEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun teamDao() : TeamDao

    companion object {

        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "football_club.db"
                    ).build()
                }
            }

            return INSTANCE as AppDatabase
        }

    }


}