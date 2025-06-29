package com.example.randomstring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.randomstring.data.RandomStringDatabase
import com.example.randomstring.data.RandomStringEntity
import com.example.randomstring.repository.RandomStringRepository
import com.example.randomstring.ui.theme.RandomStringTheme
import com.example.randomstring.uiScreens.RandomStringScreen
import com.example.randomstring.uiScreens.RandomStringState
import com.example.randomstring.uiScreens.RandomStringViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = RandomStringDatabase.getDatabase(applicationContext).randomStringDao()
        val repository = RandomStringRepository(dao, applicationContext)
        val factory = RandomStringViewModelFactory(repository)
        val viewModel: RandomStringViewModel = ViewModelProvider(this, factory)[RandomStringViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            val currentTheme = viewModel.theme.collectAsState().value

            RandomStringTheme(darkTheme = currentTheme) {
                RandomStringScreen(
                    state = viewModel.state.collectAsState().value,
                    onIntent = viewModel::handleIntent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomStringScreenPreview() {
    RandomStringTheme {
        RandomStringScreen(
            state = RandomStringState(
                list = listOf(
                    RandomStringEntity(1, "abc", 6, "2025-06-29 12:45"),
                    RandomStringEntity(2, "xyz", 6, "2025-06-29 12:46")
                ),
                isLoading = false,
                error = null
            ),
            onIntent = {}
        )
    }
}


