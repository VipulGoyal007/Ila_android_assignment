package com.intellihealth.truemeds.data.model.mixpanel

import com.google.gson.annotations.SerializedName


data class MxInternalErrorOccurred(
    @SerializedName("clicked_on_page")
    var clickedOnPage: String? = "homepage",
    @SerializedName("error_code")
    var errorCode: Int? = null,
    @SerializedName("error_statement")
    var errorStatement: String? = null,
    @SerializedName("wh_id")
    var whId: String? = null
)