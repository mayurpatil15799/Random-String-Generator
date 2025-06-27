package com.example.randomstring.uiScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.randomstring.data.RandomStringEntity

@Composable
fun RandomStringScreen(
    state: RandomStringState,
    onIntent: (RandomStringIntent) -> Unit
) {
    var maxLength by remember { mutableStateOf("10") }

    LaunchedEffect(Unit) {
        onIntent(RandomStringIntent.LoadAll)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Random String Generator", style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = maxLength,
            onValueChange = { maxLength = it },
            label = { Text("Max Length") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val length = maxLength.toIntOrNull()
                if (length != null && length > 0) {
                    onIntent(RandomStringIntent.Generate(length))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        state.error?.let {
            Text(
                text = "Error: $it",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (state.list.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onIntent(RandomStringIntent.DeleteAll) }) {
                    Text("Delete All")
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.list) { item ->
                StringItem(item = item, onDelete = {
                    onIntent(RandomStringIntent.DeleteItem(item.id))
                })
            }
        }

    }
}

@Composable
fun StringItem(item: RandomStringEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Value: ${item.value}")
                Text(text = "Length: ${item.length}")
                Text(text = "Time: ${item.timestamp}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}