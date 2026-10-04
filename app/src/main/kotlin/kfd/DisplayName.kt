package kfd

fun displayName(name: String?): String = name?.trim()?.ifBlank {"Гость"} ?: "Гость"
