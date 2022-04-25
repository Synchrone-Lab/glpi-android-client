package fr.synchrone.glpisupport.presentation.items.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

/**
 * Allow partial injection of ItemViewModel via hilt.
 *
 * @see ItemViewModel
 */

@AssistedFactory
interface ItemViewModelFactory {
    fun create(
        @Assisted("itemType") itemType: String,
        @Assisted("itemId") itemId: Int,
        @Assisted("isItemCreation") isItemCreation: Boolean,
        @Assisted("overrideSerial") overrideSerial: String?
    ): ItemViewModel
}