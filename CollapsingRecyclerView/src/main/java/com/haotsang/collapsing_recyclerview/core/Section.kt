package com.haotsang.collapsing_recyclerview.core



data class Section(
    val headerTitle: String,
    val list: List<Data>,

    var isFold: Boolean,
) {

    fun count() = list.size

    fun cloneStatusTo(other: Section) {
        other.isFold = isFold
    }

    fun clone(): Section {
        return Section(headerTitle, list, isFold)
    }
}