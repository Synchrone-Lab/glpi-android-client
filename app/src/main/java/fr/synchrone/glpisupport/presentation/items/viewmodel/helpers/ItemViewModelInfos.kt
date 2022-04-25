package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel

/**
 * Used by ItemViewModel to give to ItemViewModelHelper some context.
 *
 * @see ItemViewModel
 * @see ItemViewModelHelper
 */
data class ItemViewModelInfos (
    val isCreation: Boolean
)