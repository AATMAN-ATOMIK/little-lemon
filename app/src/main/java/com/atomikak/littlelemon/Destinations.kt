package com.atomikak.littlelemon

interface Destinations {
    val route: String
}

object Onboarding : Destinations {
    override val route = "onboarding"
}

object Home : Destinations {
    override val route: String
        get() = "Home"
}

object Profile : Destinations {
    override val route: String
        get() = "Profile"
}