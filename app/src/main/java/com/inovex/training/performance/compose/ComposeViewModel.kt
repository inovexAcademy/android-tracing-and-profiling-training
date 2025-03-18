// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inovex.training.performance.compose.models.Owner
import com.inovex.training.performance.compose.models.Project
import com.inovex.training.performance.compose.models.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComposeViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    val uiState: StateFlow<UIState> = _uiState

    var searchQuery by mutableStateOf("Kotlin")
        private set

    private val projects = mutableListOf(
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

    fun searchProjects(query: String) {
        viewModelScope.launch {
            _uiState.emit(UIState.Loading)
            _uiState.emit(UIState.Loaded(projects = projects.toList()))
        }
        projects.add(
            0, Project(
                name = "Project ${projects.size}",
                description = "Description ${projects.size}",
                url = "URL ${projects.size}",
                owner = Owner("Owner ${projects.size}")
            )
        )
    }

    fun saveSearchQuery(query: String) {
        searchQuery = query.trim()
    }

    fun onResume() {
        searchProjects(searchQuery)
    }

}