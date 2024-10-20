package com.example.windimessenger.domain.entity.profile

data class UserInfo(
    val avatarUrl: String = "",
    val avatarUri: String = "",
    val birthday: String = "",
    val city: String = "",
    val phone: String = "",
    val status: String = "",
    val username: String = "",
    val name: String = ""
) {

    fun getZodiacSign(): String {
        val birthDate = birthday.split("-")

        try {
            val day = birthDate[2].toInt()
            val month = birthDate[1].toInt()

            return when (month) {
                1 -> if (day < 20) "Козерог" else "Водолей"
                2 -> if (day < 19) "Водолей" else "Рыбы"
                3 -> if (day < 21) "Рыбы" else "Овен"
                4 -> if (day < 20) "Овен" else "Телец"
                5 -> if (day < 21) "Телец" else "Близнецы"
                6 -> if (day < 21) "Близнец" else "Рак"
                7 -> if (day < 23) "Рак" else "Лев"
                8 -> if (day < 23) "Лев" else "Дева"
                9 -> if (day < 23) "Дева" else "Весы"
                10 -> if (day < 23) "Весы" else "Скорпион"
                11 -> if (day < 22) "Скорпион" else "Стрелец"
                12 -> if (day < 22) "Стрелец" else "Козерог"
                else -> "Нажмите, чтобы добавить информацию о дне рождения"
            }
        } catch (e: Exception) {
            return "Информация отсутствует"
        }
    }
}
