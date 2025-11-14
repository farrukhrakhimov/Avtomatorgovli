package com.avtomatorgovli.feature.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avtomatorgovli.feature.reports.R

@Composable
fun ReportsRoute(viewModel: ReportsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    ReportsScreen(state)
}

@Composable
fun ReportsScreen(state: ReportsUiState) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.reports_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            state.metrics?.let { metrics ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(id = R.string.reports_today, metrics.todaySales))
                        Text(stringResource(id = R.string.reports_week, metrics.weekSales))
                        Text(stringResource(id = R.string.reports_low_stock, metrics.lowStockCount))
                    }
                }
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            Text(stringResource(id = R.string.reports_last_sales))
            LazyColumn {
                items(state.latestSales) { sale ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(stringResource(id = R.string.reports_receipt_number, sale.receiptNumber))
                            Text(stringResource(id = R.string.reports_receipt_total, sale.totalWithTax))
                        }
                    }
                }
            }
        }
    }
}
