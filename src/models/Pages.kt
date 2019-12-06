package com.fridayhack.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntityClass

const val SLUG_MAXLEN = 255

object Pages : BaseTable(name = "inbox") {
    val slug = varchar("slug", SLUG_MAXLEN).uniqueIndex()
    val body = text("body")
}

class Page(id: EntityID<Long>) : BaseEntity(id, Pages) {
    companion object : LongEntityClass<Page>(Pages)

    var slug by Pages.slug
    var body by Pages.body
}
