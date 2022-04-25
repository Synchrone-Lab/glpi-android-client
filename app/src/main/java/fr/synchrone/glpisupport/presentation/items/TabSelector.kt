package fr.synchrone.glpisupport.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel
import fr.synchrone.glpisupport.presentation.theme.AppFonts

/**
 * Composable used by item creation/edition screen to change tab.
 *
 * @see ItemComposable
 */
@Composable
fun TabSelector(modifier: Modifier,
                tabs: List<ItemViewModel.Tab>,
                selectedTab: ItemViewModel.Tab,
                onTapTab: (ItemViewModel.Tab) -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(0.dp, 16.dp)
    ) {
        tabs.forEach {
            item {
                Row {
                    val isSelected = selectedTab.displayableName(context = context) == it.displayableName(context = context)
                    Text(
                        it.displayableName(context = context),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onTapTab(it)
                            },
                        fontFamily = AppFonts.openSans,
                        fontWeight = if(isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                        color = if(isSelected) MaterialTheme.colors.onPrimary else Color.Gray,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }

}