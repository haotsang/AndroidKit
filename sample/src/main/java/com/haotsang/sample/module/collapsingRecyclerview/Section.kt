package com.haotsang.sample.module.collapsingRecyclerview



data class Section<T>(
    val headerTitle: String,
    val list: List<Data>,

    var isFold: Boolean,
) {

    fun count() = list.size

    fun cloneStatusTo(other: Section<T>) {
        other.isFold = isFold
    }

    fun clone(): Section<T> {
        return Section(headerTitle, list, isFold)
    }
}