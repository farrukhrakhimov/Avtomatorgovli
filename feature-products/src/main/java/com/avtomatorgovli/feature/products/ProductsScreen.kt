package com.avtomatorgovli.feature.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avtomatorgovli.feature.products.R

@Composable
fun ProductsRoute(viewModel: ProductsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    ProductsScreen(
        state = state,
        onQuickAdd = viewModel::saveQuickProduct
    )
}

@Composable
fun ProductsScreen(state: ProductsUiState, onQuickAdd: (String, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("0.0") }
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.products_title)) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(stringResource(id = R.string.products_total, state.products.size))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.products) { product ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(product.name)
                                Text(stringResource(id = R.string.products_price_format, product.sellingPrice))
                            }
                            Text(stringResource(id = R.string.products_stock_format, product.currentStock))
                        }
                    }
                }
            }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.products_new_name)) }
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.products_new_price)) }
            )
            Button(
                onClick = {
                    val numericPrice = price.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank() && numericPrice > 0) {
                        onQuickAdd(name, numericPrice)
                        name = ""
                        price = "0.0"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.products_add))
            }
        }
    }
}
