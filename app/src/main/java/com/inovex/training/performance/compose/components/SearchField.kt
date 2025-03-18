// SPDX-FileCopyrightText: Copyright (c) 2025 inovex GmbH
// SPDX-License: MIT

package com.inovex.training.performance.compose.components

import android.view.KeyEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inovex.training.performance.R
import com.inovex.training.performance.ui.theme.AndroidTracingAndProfilingTrainingTheme

@Composable
internal fun SearchField(
    searchInput: String,
    onValueChange: (String) -> Unit,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchInput,
            onValueChange = onValueChange,
            label = { Text(text = stringResource(id = R.string.search_term)) },
            keyboardActions = KeyboardActions(
                onSearch = { onButtonClicked() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            modifier = Modifier.onKeyEvent {
                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                    onButtonClicked()
                    true
                }
                false
            }
        )

        Button(
            onClick = onButtonClicked,
            enabled = searchInput.isNotBlank(),
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    AndroidTracingAndProfilingTrainingTheme {
        SearchField(searchInput = "Kotlin", onValueChange = {}, onButtonClicked = { })
    }
}