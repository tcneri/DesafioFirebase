package com.example.desafiofirebase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(val listGames: List<Game>, val listener:OnComicClickListener): RecyclerView.Adapter<GameAdapter.GameViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return GameViewHolder(view)
    }


    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {

//        holder.tvTit.text = "#"+comic.id.toString()
//        Picasso.get().load(img).resize(110,150).into(holder.ivComic)

    }

    override fun getItemCount() = listGames.size

    interface OnComicClickListener{
        fun gameClick(position: Int)
    }


    inner class GameViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
//        val tvTit:TextView = view.tvComic
//        val ivComic:ImageView = view.ivComic

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position)
                listener.gameClick(position)
        }
    }


}