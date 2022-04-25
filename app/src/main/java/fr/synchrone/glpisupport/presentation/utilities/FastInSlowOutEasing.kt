package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.animation.core.Easing
import kotlin.math.pow

class PowEasing(
    private val pow : Float
) : Easing {

    override fun transform(fraction: Float): Float {
        return if (fraction > 0f && fraction < 1f) {
            fraction.pow(pow)
        } else {
            fraction
        }
    }
}