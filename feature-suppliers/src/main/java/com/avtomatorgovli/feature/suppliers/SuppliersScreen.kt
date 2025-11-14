package com.avtomatorgovli.feature.suppliers

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
import com.avtomatorgovli.feature.suppliers.R

@Composable
fun SuppliersRoute(viewModel: SuppliersViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    SuppliersScreen(state)
}

@Composable
fun SuppliersScreen(state: SuppliersUiState) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.suppliers_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(state.suppliers) { supplier ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(supplier.name)
                            supplier.phone?.let { Text(it) }
                        }
                    }
                }
            }
        }
    }
}
