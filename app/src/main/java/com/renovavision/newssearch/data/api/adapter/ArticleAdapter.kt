package com.renovavision.newssearch.data.api.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.renovavision.newssearch.data.model.local.Article
import java.lang.reflect.Type

class ArticleAdapter : JsonDeserializer<Article> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Article? {
        var id: String = "0"
        var title: String? = null
        var url: String? = null
        var section: String? = null
        var thumbnail: String? = null
        var lastModified: String? = null

        if (json is JsonObject) {
            if (json.has("id")) {
                id = json.getAsJsonPrimitive("id").asString
            }

            if (json.has("webTitle")) {
                title = json.getAsJsonPrimitive("webTitle")?.asString
            }

            if (json.has("webUrl")) {
                url = json.getAsJsonPrimitive("webUrl")?.asString
            }

            if (json.has("sectionName")) {
                section = json.getAsJsonPrimitive("sectionName")?.asString
            }

            if (json.has("fields")) {
                val fields = json.getAsJsonObject("fields")

                if (fields.has("lastModified")) {
                    lastModified = fields.getAsJsonPrimitive("lastModified")?.asString
                }

                if (fields.has("thumbnail")) {
                    thumbnail = fields.getAsJsonPrimitive("thumbnail")?.asString
                }
            }
        }

        return Article(id, title, url, section, thumbnail, lastModified)
    }
}