package com.avtomatorgovli.feature.customers

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
import com.avtomatorgovli.feature.customers.R

@Composable
fun CustomersRoute(viewModel: CustomersViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    CustomersScreen(state = state)
}

@Composable
fun CustomersScreen(state: CustomersUiState) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.customers_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(state.customers) { customer ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(customer.fullName)
                            customer.phone?.let { Text(it) }
                            Text(stringResource(id = R.string.customers_points_format, customer.loyaltyPointsBalance))
                        }
                    }
                }
            }
        }
    }
}
