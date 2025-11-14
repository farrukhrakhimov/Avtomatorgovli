package com.avtomatorgovli.feature.pos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avtomatorgovli.feature.pos.R

@Composable
fun PosRoute(viewModel: PosViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    PosScreen(
        state = uiState,
        onSearch = viewModel::onSearchChanged,
        onQuantityChange = viewModel::updateQuantity,
        onFinalize = viewModel::finalizeSale
    )
}

@Composable
fun PosScreen(
    state: PosUiState,
    onSearch: (String) -> Unit,
    onQuantityChange: (Long, Double) -> Unit,
    onFinalize: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = onSearch,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.pos_search_hint)) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.cart) { item ->
                CartRow(item = item, onQuantityChange = { qty -> onQuantityChange(item.product.id, qty) })
            }
        }
        Divider()
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            SummaryRow(label = stringResource(id = R.string.pos_subtotal), value = state.subtotal)
            SummaryRow(label = stringResource(id = R.string.pos_tax), value = state.tax)
            SummaryRow(label = stringResource(id = R.string.pos_total), value = state.totalDue)
        }
        Button(
            onClick = onFinalize,
            enabled = state.cart.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.pos_pay))
        }
    }
}

@Composable
private fun CartRow(item: CartItem, onQuantityChange: (Double) -> Unit) {
    Card(modifier = Modifier
        .padding(vertical = 4.dp)
        .fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.product.name)
                Text(stringResource(id = R.string.pos_price_format, item.product.sellingPrice))
            }
            var textValue by remember(item.product.id) { mutableStateOf(item.quantity.toString()) }
            OutlinedTextField(
                value = textValue,
                onValueChange = {
                    textValue = it
                    it.toDoubleOrNull()?.let(onQuantityChange)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(96.dp)
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = stringResource(id = R.string.pos_price_format, value))
    }
}
