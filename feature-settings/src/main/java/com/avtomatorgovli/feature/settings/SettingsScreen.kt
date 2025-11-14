package com.avtomatorgovli.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import com.avtomatorgovli.feature.settings.R

@Composable
fun SettingsRoute(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    SettingsScreen(
        state = state,
        onNameChange = viewModel::updateStoreName,
        onSave = viewModel::save
    )
}

@Composable
fun SettingsScreen(state: SettingsUiState, onNameChange: (String) -> Unit, onSave: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(id = R.string.settings_title)) }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.storeName,
                onValueChange = onNameChange,
                label = { Text(stringResource(id = R.string.settings_store_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = onSave, modifier = Modifier.padding(top = 16.dp)) {
                Text(stringResource(id = R.string.settings_save))
            }
        }
    }
}
