package no.marchand.bandmates

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.marchand.bandmates.database.User

class UserAdapter internal constructor(context: Context) : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>()


    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName: TextView = itemView.findViewById(R.id.textview_display_name)
        val userAge: TextView = itemView.findViewById(R.id.textview_display_age)
        val userInstrument: TextView = itemView.findViewById(R.id.textview_display_instrument)
        val userCity: TextView = itemView.findViewById(R.id.textview_display_city)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserHolder {
       val profileView = inflater.inflate(R.layout.activity_user_profile, parent, false)
        return UserHolder(profileView)
    }

    override fun getItemCount(): Int {
       return users.size
    }

    override fun onBindViewHolder(holder: UserAdapter.UserHolder, pos: Int) {
        val currentUser = users[pos]
        holder.userName.text = currentUser.firstName + " "  + currentUser.lastName
        holder.userAge.text = currentUser.age
        holder.userInstrument.text = currentUser.instrument
        holder.userCity.text = currentUser.city
    }

    internal fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }



}