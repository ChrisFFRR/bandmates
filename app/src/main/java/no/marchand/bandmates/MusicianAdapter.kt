package no.marchand.bandmates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicianAdapter internal constructor(context: Context) : RecyclerView.Adapter<MusicianAdapter.MusicianHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var musicians = emptyList<Musician>()


    inner class MusicianHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userName: TextView = itemView.findViewById(R.id.txtView_musician_name)
        val userAge: TextView = itemView.findViewById(R.id.txtView_musician_age_info)
        val userInstrument: TextView = itemView.findViewById(R.id.txtView_musician_instrument_info)
        val userCity: TextView = itemView.findViewById(R.id.txtView_musician_city_info)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianAdapter.MusicianHolder {
       val musicianView = inflater.inflate(R.layout.musician_card_item, parent, false)

        return MusicianHolder(musicianView)
    }

    override fun getItemCount(): Int {
       return musicians.size
    }

    override fun onBindViewHolder(holder: MusicianAdapter.MusicianHolder, pos: Int) {
        val currentUser = musicians[pos]
        holder.userName.text = currentUser.firstName + " "  + currentUser.lastName
        holder.userAge.text = currentUser.age
        holder.userInstrument.text = currentUser.instrument
        holder.userCity.text = currentUser.city
    }

    internal fun setMusicians(musicians: List<Musician>) {
        this.musicians = musicians
        notifyDataSetChanged()
    }



}