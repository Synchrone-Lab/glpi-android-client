package fr.synchrone.glpisupport.presentation.items

import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelHelper

/**
 * An editable field used by ItemViewModel, ItemViewModelHelper and ItemComposable to let user change item's values.
 *
 * @property label Displayed fields name.
 * @property initialValue Initial value.
 *
 * @see ItemComposable
 * @see ItemViewModel
 * @see ItemViewModelHelper
 */
sealed class ItemField(val label: String, val initialValue: String)

class TextViewField(label: String, initialValue: String): ItemField(label, initialValue)
class CommentTextField(label: String, initialValue: String): ItemField(label, initialValue)
class LocationPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class SupplierPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class GroupPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class UserPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class SuperAdminUserPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class StatePickerField(label: String, initialValue: String): ItemField(label, initialValue)
class ManufacturerPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class DatePickerField(label: String, initialValue: String): ItemField(label, initialValue)
class BooleanField(label: String, initialValue: String): ItemField(label, initialValue)
class TypePickerField(label: String, initialValue: String): ItemField(label, initialValue)
class ModelPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class SoftwarePickerField(label: String, initialValue: String): ItemField(label, initialValue)
class SoftwareLicensePickerField(label: String, initialValue: String): ItemField(label, initialValue)
class BusinessCriticityPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class BudgetPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class DCRoomPickerField(label: String, initialValue: String): ItemField(label, initialValue)
class StaticPickerField(label: String, initialValue: String): ItemField(label, initialValue)
