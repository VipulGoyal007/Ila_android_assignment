package com.example.testapplication.app

sealed class NavigationScreens(val route:String) {
    object CategoryScreen:NavigationScreens("main_screen")
    object DetailScreen:NavigationScreens("detail_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach { args->
                append("/$args")
            }
        }
    }
}