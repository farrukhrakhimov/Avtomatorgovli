package com.avtomatorgovli.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.avtomatorgovli.R

@Composable
fun DashboardScreen(
    onOpenPos: () -> Unit,
    onOpenInventory: (() -> Unit)? = null,
    onOpenPurchases: (() -> Unit)? = null,
    onOpenSuppliers: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(id = R.string.dashboard_title))
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(id = R.string.dashboard_today_placeholder))
                Text(stringResource(id = R.string.dashboard_actions))
            }
        }
        Button(onClick = onOpenPos) {
            Text(stringResource(id = R.string.dashboard_open_pos))
        }
        onOpenInventory?.let {
            Button(onClick = it, modifier = Modifier.padding(top = 8.dp)) {
                Text(stringResource(id = R.string.dashboard_open_inventory))
            }
        }
        onOpenPurchases?.let {
            Button(onClick = it, modifier = Modifier.padding(top = 8.dp)) {
                Text(stringResource(id = R.string.dashboard_open_purchases))
            }
        }
        onOpenSuppliers?.let {
            Button(onClick = it, modifier = Modifier.padding(top = 8.dp)) {
                Text(stringResource(id = R.string.dashboard_open_suppliers))
            }
        }
    }
}
