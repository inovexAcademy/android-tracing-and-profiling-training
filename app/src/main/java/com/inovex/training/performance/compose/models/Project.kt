// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose.models

data class Project(
    val name: String,
    val description: String,
    val url: String,
    val owner: Owner,
)

data class Owner(
    var login: String
)
