package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.applanguages.views

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton

class LanguagesAdapterView(
    languages: ArrayList<String>,
    selected: Int,
    onLanguageListener: OnLanguageListener
) : RecyclerView.Adapter<LanguagesAdapterView.ViewHolder>() {
    private val languages: ArrayList<String>
    private val onLanguageListener: OnLanguageListener

    init {
        mPreviousIndex = selected
        this.languages = languages
        this.onLanguageListener = onLanguageListener
    }

    class ViewHolder(view: View, onLanguageListener: OnLanguageListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        val languageTitleTextView: AppCompatTextView =
            itemView.findViewById(R.id.textView_languageName)
        val image: PETImageButton = itemView.findViewById(R.id.imageView_languageChoiceIcon)
        val background: Drawable? =
            itemView.findViewById<View>(R.id.constraintLayout73)?.background
        private val onLanguageListener: OnLanguageListener

        init {
            view.setOnClickListener(this)
            this.onLanguageListener = onLanguageListener
        }

        override fun onClick(v: View) {
            onLanguageListener.onNoteClick(adapterPosition)
            mPreviousIndex = adapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val messageView = inflater.inflate(R.layout.item_language, parent, false)
        return ViewHolder(messageView, this.onLanguageListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textView = holder.languageTitleTextView
        textView.text = languages[position]
        textView.isSelected = true

        //color on item unselecting item
        holder.image.visibility = if (mPreviousIndex == position) View.VISIBLE else View.INVISIBLE
        holder.background?.setLevel(if (mPreviousIndex == position) 1 else 0)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    interface OnLanguageListener {
        fun onNoteClick(position: Int)
    }

    companion object {
        private var mPreviousIndex = 0
    }
}