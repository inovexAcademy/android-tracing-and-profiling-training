// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance

import org.junit.Test
import kotlin.time.measureTime

class ListMapTest {

    data class Project(val name: String, val ownerId: String)
    data class Owner(val id: String, val name: String)

    data class ProjectWithOwner(val name: Project, val owner: Owner)

    private fun produceProjectsWithOwners(
        projects: List<Project>,
        owners: List<Owner>
    ): List<ProjectWithOwner> {
        return projects.map { project ->
            val owner = owners
                .first { it.id == project.ownerId }
            ProjectWithOwner(project, owner)
        }
    }

    private fun produceProjectsWithOwnersMap(
        projects: List<Project>,
        owners: List<Owner>
    ): List<ProjectWithOwner> {
        val ownersById = owners.associateBy { it.id }
        return projects.map { project ->
            val owner = ownersById[project.ownerId]
            ProjectWithOwner(project, owner!!)
        }
    }

    private val owners = (0 until 10_000).map { Owner(id = it.toString(), name = "Owner $it") }

    private val projects =
        (0 until 100_000).map { Project(name = "Project $it", ownerId = owners[it % 9_999].id) }

    @Test
    fun listPerformance() {
        val duration = measureTime {
            produceProjectsWithOwners(projects, owners)
        }
        println("list duration: $duration")
    }

    @Test
    fun mapPerformance() {
        val duration = measureTime {
            produceProjectsWithOwnersMap(projects, owners)
        }
        println("list duration: $duration")
    }
}