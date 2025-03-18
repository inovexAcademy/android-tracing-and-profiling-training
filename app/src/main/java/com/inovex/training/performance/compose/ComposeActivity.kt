// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.inovex.training.performance.R
import com.inovex.training.performance.compose.components.Projects
import com.inovex.training.performance.compose.components.SearchField
import com.inovex.training.performance.compose.models.UIState
import com.inovex.training.performance.ui.theme.AndroidTracingAndProfilingTrainingTheme

@OptIn(ExperimentalMaterial3Api::class)
class ComposeActivity : ComponentActivity() {
    private val viewModel: ComposeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTracingAndProfilingTrainingTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                fontWeight = FontWeight.Bold
                            )
                        })
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding), color = MaterialTheme.colorScheme.background
                    ) {
                        Column {
                            SearchField(
                                searchInput = viewModel.searchQuery,
                                onValueChange = { viewModel.saveSearchQuery(it) },
                                onButtonClicked = { viewModel.searchProjects(viewModel.searchQuery) }
                            )
                            Text(
                                text = stringResource(id = R.string.results),
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            when (val state = uiState) {
                                is UIState.Error -> Text(
                                    text = state.message,
                                    modifier = Modifier.padding(8.dp)
                                )

                                is UIState.Loaded -> Projects(state.projects)
                                UIState.Loading -> Text(
                                    text = stringResource(id = R.string.loading),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
