package kfd

//fun canSendMessage(
//    text: String?,
//    maxLength: Int = 140
//): Boolean = text!!.isNotEmpty() && text.length < maxLength

fun canSendMessage(
    text: String?,
    maxLength: Int = 140
): Boolean {
    if (text == null) {
        return false
    }
    if (text.isEmpty()) {
        return false
    }
    if (text.isBlank()) {
        return false
    }
    return text.length < maxLength
}