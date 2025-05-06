package com.example.loukatah.presentation.view.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Sealed class representing all navigation destinations in the app
 */
sealed class Screen(val route: String) {
    /**
     * Home screen showing the list of items
     */
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")

    object Account : Screen("account")

    object Settings : Screen("settings")


    object ItemDetail : Screen("item_detail/{$ITEM_ID_KEY}") {
        const val ITEM_ID_KEY = "itemId"
        
        val arguments = listOf(
            navArgument(ITEM_ID_KEY) {
                type = NavType.StringType
            }
        )
        
        fun createRoute(itemId: String): String {
            return "item_detail/$itemId"
        }
    }
    

    object AddItem : Screen("add_item")


    
    companion object {
        const val ITEM_ID_KEY = "itemId"
    }
}
