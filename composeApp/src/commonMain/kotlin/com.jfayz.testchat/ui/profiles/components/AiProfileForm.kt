package com.jfayz.testchat.ui.profiles.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//@Composable
//fun AiProfileForm() {
//    // Compose dialog view for AI profile form. It contains input for name, Api key, dropdown for AI type and button to save changes.
//
//}

@Composable
fun AiProfileForm(
    onDismissRequest: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var name: String by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    var aiType by remember { mutableStateOf("") }
    val aiTypes = listOf("Type1", "Type2", "Type3") // Replace with your actual AI types
    var dropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("AI Profile Form") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("API Key") }
                )
                Box() {
                    TextButton(onClick = { dropdownExpanded = true }) {
                        Text(if (aiType.isEmpty()) "Select AI Type" else aiType)
                    }
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        aiTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    aiType = type
                                    dropdownExpanded = false
                                })
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, apiKey, aiType) }) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}
