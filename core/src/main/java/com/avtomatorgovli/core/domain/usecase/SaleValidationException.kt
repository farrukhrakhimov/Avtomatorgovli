package com.avtomatorgovli.core.domain.usecase

/**
 * Domain level exception that represents incorrect POS input.
 * Message text is kept in Russian to be shown directly inside UI snackbar/dialogs.
 */
class SaleValidationException(message: String) : IllegalArgumentException(message)
