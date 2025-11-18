package com.uk.ac.tees.mad.hydrateme.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.uk.ac.tees.mad.hydrateme.data.local.WaterLogDao
import com.uk.ac.tees.mad.hydrateme.data.local.WaterLogEntity
import com.uk.ac.tees.mad.hydrateme.presentation.home.WaterLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

interface WaterDataRepository {
    fun getTodayLogs(): Flow<List<WaterLog>>
    suspend fun addWaterLog(amount: Int)
    suspend fun syncLogs()
}

class WaterDataRepositoryImpl(
    private val waterLogDao: WaterLogDao,
    private val firestore: FirebaseFirestore,
    private val userId: String // Placeholder for user ID
) : WaterDataRepository {

    private val waterLogCollection = firestore.collection("users").document(userId).collection("water_logs")

    override fun getTodayLogs(): Flow<List<WaterLog>> {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return waterLogDao.getLogsForDay(startOfDay).map { entities ->
            entities.map { it.toWaterLog() }
        }
    }

    override suspend fun addWaterLog(amount: Int) {
        val newLog = WaterLogEntity(
            amount = amount,
            timestamp = Date()
        )
        waterLogDao.insertLog(newLog)
        waterLogCollection.add(newLog).await()
    }

    override suspend fun syncLogs() {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val remoteLogs = waterLogCollection.whereGreaterThanOrEqualTo("timestamp", startOfDay).get().await()
        for (document in remoteLogs) {
            val log = document.toObject<WaterLogEntity>()
            waterLogDao.insertLog(log)
        }
    }
}

fun WaterLogEntity.toWaterLog(): WaterLog {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return WaterLog(
        time = timeFormat.format(this.timestamp),
        amount = this.amount
    )
}
