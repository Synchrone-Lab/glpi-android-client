package fr.synchrone.glpisupport.data.model.remote.items

/**
 * Store infos of how item is represented in API.
 *
 * @property typeName Item type used in API urls.
 *
 * @see ItemApi
 */
interface ItemApiCompanionObject {
    val typeName: String
}