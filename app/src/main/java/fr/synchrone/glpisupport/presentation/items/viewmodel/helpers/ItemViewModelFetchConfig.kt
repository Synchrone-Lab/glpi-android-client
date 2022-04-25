package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel

/**
 * Tell what data needs to be fetched by ItemViewModel.
 *
 * @see ItemViewModel
 */
data class ItemViewModelFetchConfig(
    val needEntities: Boolean,
    val needProfiles: Boolean,
    val needSuperAdminProfilesUsers: Boolean,
    val needUsers: Boolean,
    val needLocations: Boolean,
    val needGroups: Boolean,
    val needSuppliers: Boolean,
    val needStates: Boolean,
    val needManufacturers: Boolean,
    val needModels: Boolean,
    val needTypes: Boolean,
    val needSoftwares: Boolean = false,
    val needSoftwaresLicenses: Boolean = false,
    val needDCRooms: Boolean = false
)