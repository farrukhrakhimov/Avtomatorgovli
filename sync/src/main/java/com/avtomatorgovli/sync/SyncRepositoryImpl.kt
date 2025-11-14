package com.avtomatorgovli.sync

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.avtomatorgovli.core.domain.repository.SyncRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SyncRepository {
    override suspend fun enqueueFullSync() {
        Timber.i("Sync requested")
        val request = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "full_sync",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
