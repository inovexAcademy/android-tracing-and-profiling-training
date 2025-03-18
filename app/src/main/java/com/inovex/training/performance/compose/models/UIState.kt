// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose.models

sealed interface UIState {
    data class Error(val message: String) : UIState
    data class Loaded(val projects: List<Project>) : UIState
    data object Loading : UIState
}
