package com.uk.ac.tees.mad.hydrateme.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WaterLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: WaterLogEntity)

    @Query("SELECT * FROM water_logs WHERE timestamp >= :startOfDay")
    fun getLogsForDay(startOfDay: Date): Flow<List<WaterLogEntity>>

}
