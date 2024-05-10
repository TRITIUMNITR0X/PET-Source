package com.TritiumGaming.phasmophobiaevidencepicker.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser
import com.google.firebase.auth.FirebaseUser

class AccountDetailsView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(getContext())
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(getContext())
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(getContext())
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(getContext())
    }

    fun init(context: Context?) {
        inflate(context, R.layout.layout_account_details_2, this)

        val user = FirestoreUser.getCurrentFirebaseUser()

        //try {
            setUsernameInitials(user)
        /*} catch (e: Exception) {
            e.printStackTrace()
        }*/
        setContainerVisibility(user)
    }

    private fun setContainerVisibility(user: FirebaseUser?) {
        visibility = if (user == null) { GONE } else { VISIBLE }
    }

    @Throws(Exception::class)
    private fun setUsernameInitials(user: FirebaseUser?) {
        /*if (user == null) {
            throw FirestoreUser.NullFirebaseUserException()
        }*/

        val accountIcon = findViewById<AccountIconView>(R.id.account_icon)
        accountIcon?.createAccountInitials(user?.displayName)
    }
}
