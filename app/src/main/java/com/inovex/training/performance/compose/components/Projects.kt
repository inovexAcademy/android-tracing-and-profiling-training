// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inovex.training.performance.compose.models.Owner
import com.inovex.training.performance.compose.models.Project
import com.inovex.training.performance.ui.theme.AndroidTracingAndProfilingTrainingTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun Projects(projects: List<Project>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(
            items = projects,
        ) { project ->
            Project(project)
        }
    }
}

@Composable
fun Project(project: Project, modifier: Modifier = Modifier) {
    val projectSize =
        project.name.length + project.description.length + project.url.length + project.owner.login.length
    Card(
        modifier = modifier
            .padding(all = 8.dp)
    ) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Column {
                Row {
                    Text(text = project.name, modifier = Modifier.weight(1F))
                    Text(text = project.owner.login, fontStyle = FontStyle.Italic)
                }
                Text(color = MaterialTheme.colorScheme.secondary, text = project.description)
                Text(color = MaterialTheme.colorScheme.inversePrimary, text = project.url)
                Text(color = MaterialTheme.colorScheme.primary, text = "$projectSize")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectsPreview() {
    AndroidTracingAndProfilingTrainingTheme {
        Projects(
            persistentListOf(
                Project(
                    name = "kotlinx.serialization",
                    description = "Kotlin multiplatform / multi-format serialization",
                    url = "https://github.com/Kotlin/kotlinx.serialization",
                    owner = Owner(login = "Kotlin"),
                ),
                Project(
                    name = "kotlin-by-example",
                    description = "The sources of Kotlin by Example.",
                    url = "https://github.com/Kotlin/kotlin-by-example",
                    owner = Owner(login = "Kotlin"),
                ),
                Project(
                    name = "kotlin-in-action",
                    description = "Code samples from the \\\"Kotlin in Action\\\" book",
                    url = "https://github.com/Kotlin/kotlin-in-action",
                    owner = Owner(login = "Kotlin"),
                )
            )
        )
    }
}