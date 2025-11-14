package com.avtomatorgovli.core.domain.model

data class PosCartItem(
    val product: Product,
    val quantity: Double,
    val discountPercent: Double
) {
    val lineTotalWithoutTax: Double
        get() = quantity * product.sellingPrice * (1 - discountPercent / 100.0)
    val lineTax: Double
        get() = lineTotalWithoutTax * product.taxRatePercent / 100.0
    val lineTotalWithTax: Double
        get() = lineTotalWithoutTax + lineTax
}
