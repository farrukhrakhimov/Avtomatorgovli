package com.avtomatorgovli.core.domain.repository

interface SyncRepository {
    suspend fun enqueueFullSync()
}
