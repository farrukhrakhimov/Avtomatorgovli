package com.avtomatorgovli.feature.purchases

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.avtomatorgovli.feature.purchases.R

@Composable
fun PurchasesRoute(viewModel: PurchasesViewModel = hiltViewModel()) {
    PurchasesScreen(onDemoPurchase = viewModel::createDemoPurchase)
}

@Composable
fun PurchasesScreen(onDemoPurchase: (Long, Double, Double, Long) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.purchases_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.purchases_hint))
            Button(onClick = { onDemoPurchase(1L, 5.0, 100.0, 1L) }, modifier = Modifier.padding(top = 16.dp)) {
                Text(stringResource(id = R.string.purchases_create_demo))
            }
        }
    }
}
