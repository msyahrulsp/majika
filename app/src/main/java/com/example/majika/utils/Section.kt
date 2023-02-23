package com.example.majika.utils

import com.example.majika.models.Menu

class Section {
    private var headerTitle: String = ""
    private var items: List<Menu> = listOf()

     constructor(headerTitle: String, items: List<Menu>) {
        this.headerTitle = headerTitle
        this.items = items
    }

    fun getHeaderTitle(): String {
        return headerTitle
    }

    fun getItems(): List<Menu> {
        return items
    }

    @Override
    override fun toString(): String {
        return items.toString()
    }
}