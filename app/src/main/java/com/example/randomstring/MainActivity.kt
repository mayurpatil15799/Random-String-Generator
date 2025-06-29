package com.example.randomstring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomstring.data.RandomStringDao
import com.example.randomstring.data.RandomStringDatabase
import com.example.randomstring.data.RandomStringEntity
import com.example.randomstring.ui.theme.RandomStringTheme
import com.example.randomstring.uiScreens.RandomStringScreen
import com.example.randomstring.uiScreens.RandomStringState
import com.example.randomstring.uiScreens.RandomStringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: RandomStringViewModel by viewModels()
    lateinit var dao: RandomStringDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = RandomStringDatabase.getDatabase(applicationContext).randomStringDao()
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


