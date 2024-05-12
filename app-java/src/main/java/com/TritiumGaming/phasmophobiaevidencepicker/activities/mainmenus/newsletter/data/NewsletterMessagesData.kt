package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsletterMessagesData {
    var ready: Boolean = false
    var requiresNotify: Boolean = false

    private var lastReadDate = "NA"
    private var mostRecentDateFound = "NA"

    var inboxType: NewsletterViewModel.InboxType? = null
    val messages: ArrayList<NewsletterMessageData> = ArrayList()

    fun add(msg: NewsletterMessageData) {
        setMostRecentDate(msg.date)

        messages.add(msg)
    }

    fun setLastReadDate(date: String?) {
        if (date == null) {
            Log.d("Message Center", "Date is null")
            return
        }

        val parser =
            SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
        val simpleFormatter =
            SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)

        var output = "NA"
        try {
            val formattedDate = parser.parse(date)
            if (formattedDate != null) {
                output = simpleFormatter.format(formattedDate)
            }
        } catch (e: ParseException) {
            output = date
        }

        lastReadDate = output
    }

    fun setMostRecentDate(date: String?) {
        if (date == null) {
            return
        }

        if (messages.isEmpty()) {
            val parser =
                SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
            val simpleFormatter =
                SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)

            var output = ""
            try {
                val parsedDate = parser.parse(date)
                if (parsedDate != null) {
                    output = simpleFormatter.format(parsedDate)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            mostRecentDateFound = output
        }
    }

    fun compareDates(): Boolean {
        return !mostRecentDateFound.equals(lastReadDate, ignoreCase = true)
            .also { requiresNotify = it }
    }

    fun getLastReadDate(): String {
        return lastReadDate
    }

    fun updateLastReadDate() {
        lastReadDate = mostRecentDateFound

        compareDates()
    }

    override fun toString(): String {
        val t = StringBuilder()

        for (m in messages) {
            t.append("\n[").append(m.title).append(" ").append(m.date).append(" ")
                .append(m.description).append("]")
        }
        return t.toString()
    }
}
