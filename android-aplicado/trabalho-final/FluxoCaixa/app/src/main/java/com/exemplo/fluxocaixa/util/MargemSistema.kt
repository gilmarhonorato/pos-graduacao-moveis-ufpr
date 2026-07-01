package com.exemplo.fluxocaixa.util

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object MargemSistema {

    fun aplicar(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { area, insets ->
            val margens = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            area.setPadding(margens.left, margens.top, margens.right, margens.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(view)
    }
}
