package com.example.a20_recyclerviewandr

import android.app.Application
import com.example.a20_recyclerviewandr.model.UsersService

//Класс App - синглтон, существует в одном экземпляре и стартует раньше всех.
// Это хорошее место для инициализации и для хранения ссылок на другие синглтоны.
// Если не прописать в манифесте - то стартовать он не будет.
class App: Application() {
    val usersService = UsersService()
}