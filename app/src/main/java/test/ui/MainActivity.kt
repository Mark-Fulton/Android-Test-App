package test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import test.ui.aeroplane_list.AeroplaneListScreen
import test.ui.aeroplane_list.PortfolioViewModel
import test.ui.theme.AerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AerTheme {
                val portfolioViewModel: PortfolioViewModel  = viewModel()
                AeroplaneListScreen(portfolioViewModel)
            }
        }
    }
}
