package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import fr.synchrone.glpisupport.R

@Composable
fun WavesComposable(
    reversed: Boolean = false,
    wavesXOffset: Dp,
    wavesYOffset: Dp,
    color: Color = MaterialTheme.colors.primary,
    background: Color = MaterialTheme.colors.background
) {
    ConstraintLayout(
        decoupledConstraints(reversed = reversed),
        modifier = Modifier
            .fillMaxSize()
            .offset(y = wavesYOffset)
    ) {
        Image(
            painterResource(id = R.drawable.waves1),
            "waves",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(background),
            modifier = Modifier.rotate(if(reversed) 180f else 0f).layoutId("wavesImage").offset(x = wavesXOffset).background(color),
        )
        Box(modifier = Modifier.layoutId("wavesSpacer").background(color)) {
            Spacer(modifier = Modifier.fillMaxSize())
        }
    }
}

private fun decoupledConstraints(reversed: Boolean): ConstraintSet {
    return ConstraintSet {
        val wavesImage = createRefFor("wavesImage")
        val wavesSpacer = createRefFor("wavesSpacer")

        constrain(wavesImage) {
            if(reversed) {
                bottom.linkTo(parent.bottom)
                absoluteRight.linkTo(parent.absoluteRight)
            } else {
                top.linkTo(parent.top)
                absoluteLeft.linkTo(parent.absoluteLeft)
            }
            width = Dimension.percent(3f)
            height = Dimension.value(150.dp)
        }

        constrain(wavesSpacer) {
            if (reversed) {
                bottom.linkTo(wavesImage.top)
                top.linkTo(parent.top)
            } else {
                top.linkTo(wavesImage.bottom)
                bottom.linkTo(parent.bottom)
            }
            absoluteLeft.linkTo(parent.absoluteLeft)
            absoluteRight.linkTo(parent.absoluteRight)
            height = Dimension.fillToConstraints
        }
    }
}