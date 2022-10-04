package com.example.a20_recyclerviewandr.model


import com.github.javafaker.Faker
import java.util.*
import kotlin.collections.ArrayList

//Это самая простейшая реализация модели, то есть классов отвечающих за данные и бизнес-логику.
// В реальных приложениях пользователи в списке появлялись бы не из воздуха,
// а например из базы или сервера, и тогда бы UsersService этим бы и занимался.

//Сделаем класс синглтоном создаем класс app


//объявляем тип слушателя , чтобы знать когда данные меняются.
// typealias - альтернативное название типа. Ссылка на тип
typealias UsersListener = (users:List<User>) -> Unit

class UsersService {
    private var users = mutableListOf<User>()
    //переменная в которую будут складываться все слушатели
    private val listeners = mutableListOf<UsersListener>()


    init {
        val faker = Faker.instance()
        //перемешивает
        IMAGES.shuffle()
        //берем цифры от 1 до 100 и преобразуем их в объекты класса User
        users = (1..100).map {User(
            //it это числа от 1 до 100
            id = it.toLong(),
            // случайно генерируются названия из библиотеки faker
            name = faker.name().name(),
            company = faker.company().name(),
            //в индексе остаток от деления
            photo = IMAGES[it % IMAGES.size]
        ) }.toMutableList()
    }
    fun getUsers(): List<User>{
        return users
    }
    fun deleteUser(user: User){
        // можно так, но если метод преопределен в классе, то не сработает
        // users.remove(user)

        //лучше удалять по индексу. indexOfFirst проходит по списку и
        // возвращает индекс элемента, при выполнении условия
        //если не выполняется условие, то возвращает -1
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if ( indexToDelete != -1){
            users = ArrayList(users)
            users.removeAt(indexToDelete)
            notifyChanges()
        }

    }
    //премещение пользователя по списку
    fun moveUser(user: User, moveBy: Int){
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return
        //проверяем на отрицательное значение и чтобы не выходил за размер
        var newIndex = oldIndex + moveBy
        if ( newIndex < 0 || newIndex >= users.size) return
        users = ArrayList(users)
        //метод меняет местами элементы
        Collections.swap(users,oldIndex,newIndex)
        notifyChanges()
    }
    //добавить слушатель
    fun addListener(listener: UsersListener){
        listeners.add(listener)
        //Передает список пользователей слушателю, который был назначен через addListener.\
        // В результате при каждом изменении данных, в MainActivity будет вызван код внутри usersListener
        // (строки 51-53 MainActivity), где актуальный список пользователей назначается адаптеру для отображения в списке.
        listener.invoke(users)
    }

    //удалить слушатель
    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }
    //уведомление слушателей
    private fun notifyChanges(){
        //проходим список слушателей и передаем текущий список пользователей
        listeners.forEach{it.invoke(users)}
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"
        )
    }
}