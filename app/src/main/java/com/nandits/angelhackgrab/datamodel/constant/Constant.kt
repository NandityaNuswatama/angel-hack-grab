package com.nandits.angelhackgrab.datamodel.constant

import com.nandits.angelhackgrab.datamodel.Tip

fun getTips(): List<Tip> {
    return listOf(
        Tip("Rp. 2.000", false),
        Tip("Rp. 5.000", false),
        Tip("Rp. 7.000", false),
        Tip("Rp. 10.000", false),
        Tip("Rp. 20.000", false),
        Tip("Rp. 50.000", false),
    )
}