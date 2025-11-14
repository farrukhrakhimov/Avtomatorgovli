package com.avtomatorgovli.feature.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.avtomatorgovli.feature.inventory.R

@Composable
fun InventoryRoute(viewModel: InventoryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    InventoryScreen(state)
}

@Composable
fun InventoryScreen(state: InventoryUiState) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.inventory_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(state.lowStock) { product ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(product.name)
                            Text(stringResource(id = R.string.inventory_stock_format, product.currentStock, product.minStockLevel))
                        }
                    }
                }
            }
        }
    }
}
