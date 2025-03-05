# SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH

cmake_minimum_required(VERSION 3.18.1)

project("native")

add_library(${PROJECT_NAME} SHARED native.c)
target_compile_options(${PROJECT_NAME} PRIVATE -Wall -Werror -Wextra)
find_library(log-lib log)
find_library(android-lib android)
target_link_libraries(${PROJECT_NAME} ${log-lib} ${android-lib})
