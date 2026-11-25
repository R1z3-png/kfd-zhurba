package kfd
import java.time.LocalDate

@JvmInline value class Discount(val percent: Int) {
    init {
        require(percent in 1..90) { "discount должен быть 1..90, получено $percent" }
    }
}
@JvmInline value class Code(val code: String) {
    init {
        require(code.length in 4..16 ) { "недопустимая длина кода" }
        require(code.matches(Regex("^[A-Z0-9]+$")) ) { "недопустимые символы в коде" }
    }
}
@JvmInline
value class UsageLimit(val value: Int) {
    init {
        require(value > 0) {"Лимит применений должен быть положительным, получено $value" }
    }
}

data class Coupon(
    val code: Code,
    val discount: Discount,
    val expiresOn: LocalDate,
    val usageLimit: UsageLimit = UsageLimit(1)
)

fun main() {

    val coupon = Coupon(
        code = Code("SALE2026"),
        discount = Discount(25),
        expiresOn = LocalDate.of(2026, 12, 31),
        usageLimit = UsageLimit(1)
    )
    println(coupon)
    coupon.copy(discount = Discount(999))
}