package com.example.loukatah.presentation.view.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loukatah.presentation.view.screens.AccountScreen
import com.example.loukatah.presentation.view.screens.HomeScreen
import com.example.loukatah.presentation.view.screens.ItemDetailScreen
import com.example.loukatah.presentation.view.screens.AddItemScreen
import com.example.loukatah.presentation.view.screens.SettingsScreen


@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    onItemClick = { itemId ->
                        navController.navigate(Screen.ItemDetail.createRoute(itemId))
                    },
                    onAddItemClick = {
                        navController.navigate(Screen.AddItem.route)
                    }
                )
            }

            composable(route = Screen.Account.route) {

                AccountScreen()

            }

            composable(route = Screen.Settings.route) {

                SettingsScreen()

            }

            composable(
                route = Screen.ItemDetail.route,
                arguments = Screen.ItemDetail.arguments
            ) {
                val itemId = it.arguments?.getString(Screen.ItemDetail.ITEM_ID_KEY) ?: ""
                ItemDetailScreen(
                    itemId = itemId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.AddItem.route) {
                AddItemScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onItemAdded = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
