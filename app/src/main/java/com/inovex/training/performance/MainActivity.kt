// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.inovex.training.performance.compose.ComposeActivity
import com.inovex.training.performance.ui.theme.AndroidTracingAndProfilingTrainingTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTracingAndProfilingTrainingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(stringResource(R.string.app_name)) })
                    }
                ) { innerPadding ->
                    val context = LocalContext.current
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Button(onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        CustomTraceEventsActivity::class.java
                                    )
                                )
                            }) {
                                Text("CustomTraceEventsActivity")
                            }
                            Button(onClick = {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ComposeActivity::class.java
                                    )
                                )
                            }) {
                                Text("ComposeActivity")
                            }
                        }
                    }
                }
            }
        }
    }
}
