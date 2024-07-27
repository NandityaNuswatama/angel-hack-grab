package com.nandits.angelhackgrab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nandits.angelhackgrab.screen.delivery.DeliveryScreen
import com.nandits.angelhackgrab.screen.merchant.MerchantScreen
import com.nandits.angelhackgrab.screen.rating.RatingScreen
import com.nandits.angelhackgrab.ui.theme.AngelHackGrabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AngelHackGrabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.statusBarsPadding(),
        navController = navController, startDestination = "main"
    ) {
        composable("main") { (MerchantScreen(navController)) }
        composable(
            "delivery/{orderId}/{driverId}",
            arguments = listOf(
                navArgument("orderId") { type = NavType.IntType },
                navArgument("driverId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            DeliveryScreen(
                backStackEntry.arguments?.getInt("orderId") ?: 0,
                backStackEntry.arguments?.getInt("driverId") ?: 0,
                navController
            )
        }
        composable(
            "rating/{driverPhotoUrl}/{driverId}/{stickerUrl}/{totalPrice}",
            arguments = listOf(
                navArgument("driverPhotoUrl") { type = NavType.StringType },
                navArgument("driverId") { type = NavType.IntType },
                navArgument("stickerUrl") { type = NavType.StringType },
                navArgument("totalPrice") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            RatingScreen(
                backStackEntry.arguments?.getString("driverPhotoUrl") ?: "",
                backStackEntry.arguments?.getInt("driverId") ?: 0,
                backStackEntry.arguments?.getString("stickerUrl") ?: "",
                backStackEntry.arguments?.getString("totalPrice") ?: "",
                navController
            )
        }
    }
}