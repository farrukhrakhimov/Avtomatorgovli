package com.avtomatorgovli.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.avtomatorgovli.core.data.local.entity.CustomerEntity
import com.avtomatorgovli.core.data.local.entity.LoyaltyTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers WHERE (:query == '' OR fullName LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' OR cardNumber = :query) AND isDeleted = 0 ORDER BY fullName")
    fun observeCustomers(query: String): Flow<List<CustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(customer: CustomerEntity)

    @Query("SELECT * FROM customers WHERE cardNumber = :card LIMIT 1")
    suspend fun getByCard(card: String): CustomerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: LoyaltyTransactionEntity)

    @Query("SELECT * FROM loyalty_transactions WHERE customerId = :customerId ORDER BY dateTime DESC")
    fun observeTransactions(customerId: Long): Flow<List<LoyaltyTransactionEntity>>
}
