package com.example.buildmovieapponline.View_Activities.DetailMovie

enum class MovieCategory(val id: Int, val displayName: String) {
    AD(1, "Quảng cáo"),
    NEW(2, "Phim mới"),
    OTHER(3, "Khác");

    companion object {
        fun fromId(id: String?): String {
            return id?.let {
                values().firstOrNull { category -> category.id == it.toInt() }?.displayName ?: "Không xác định"
            } ?: "Không xác định"
        }
    }
}
