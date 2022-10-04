package com.example.a20_recyclerviewandr

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a20_recyclerviewandr.databinding.ItemUserBinding
import com.example.a20_recyclerviewandr.model.User
//объявляем интерфейс для разных действий
interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun OnUserDelete(user: User)
    fun onUserDetails(user: User)
}
//передаем userActionListener в конструктор адаптера
class UsersAdapter( private  val actionListener: UserActionListener)
//в <> должен быть viewholder. Создаем его в теле класса
    : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener
{
    //список пользователей с функцией уведомления при изменении списка
    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onClick(v: View) {
        //получаем пользователя
        val user: User = v.tag as User
        //проверяем на что нажал
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> { actionListener.onUserDetails(user) }
        }

    }

    //возвращает количество элементов в списке
    override fun getItemCount(): Int {
        var test = users.size
        return users.size
    }

    //используется для создания нового элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        //создаем инфлэйтер
        val inflater = LayoutInflater.from(parent.context)
        //
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        //создаем и возвращаем экземпляр класса holder

        //инициализируем слушатели, root - элемент списка и кнопка more.
        // this можно передать, потомучто адаптер реализует интерфейс View.OnClickListener
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    //обновить элемент списка
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {


        // получили ЭЛЕМЕНТ СПИСКА
        val user = users[position]
        //отрисовываем элемент через биндинг
        with(holder.binding) {
            //возвращаем в tag значения. При нажатии на них вызывается onClick
            holder.itemView.tag = user
            moreImageViewButton.tag = user

            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
//если в onBindViewHolder применяется ветвление, то нужно обновление в обоих ветках
            if (user.photo.isNotBlank()) {
                //используется библиотека Glide
                Glide.with(photoImageView.context)
                    //предаем ссылку на фотографию
                    .load(user.photo)
                    //делаем круглой
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else {
                //отменить загрузку для закешированного изображения
                Glide.with(photoImageView.context).clear(photoImageView)
                //берем из библиотеки изображение
                photoImageView.setImageResource(R.drawable.ic_user_avatar)

            }
        }
    }
    private fun showPopupMenu (view: View){
        val popupMenu = PopupMenu(view.context, view)
        //для доступа к ресурсам стринг
        val context: Context = view.context
        //получаем юзера
        val user = view.tag as User
        //получаем позицию элемента
        val position = users.indexOfFirst { it.id == user.id }

        //в apply проверяем последний или первый, чтобы не выйти за диапазон
        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Menu Up").apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Menu Down").apply {
            isEnabled = position < users.size -1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                ID_MOVE_UP -> {actionListener.onUserMove(user, -1)}
                ID_MOVE_DOWN -> {actionListener.onUserMove(user, 1)}
                ID_REMOVE -> {actionListener.OnUserDelete(user)}
            }
            return@setOnMenuItemClickListener true
        }
        //показать попап меню
        popupMenu.show()
    }

    //создаем viewholder с помощью binding
    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)
    companion object{
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }

}