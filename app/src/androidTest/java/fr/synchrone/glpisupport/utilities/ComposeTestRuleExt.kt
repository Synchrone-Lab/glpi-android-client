package fr.synchrone.glpisupport.utilities

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import fr.synchrone.glpisupport.presentation.base.MainActivity

fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.waitForNode(matcher: SemanticsMatcher) {
    this.waitUntil(30000) {
        try {
            this.onNode(matcher).assertIsDisplayed()
            true
        } catch(e: AssertionError) {
            false
        }
    }
}