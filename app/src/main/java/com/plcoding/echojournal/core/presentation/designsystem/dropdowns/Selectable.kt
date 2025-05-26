package com.plcoding.echojournal.core.presentation.designsystem.dropdowns

data class Selectable<T>(
    val item: T,
    val selected: Boolean
) {
    companion object {
        fun <T> List<T>.asUnSelectedItems(): List<Selectable<T>> {
            return map {
                Selectable(
                    item = it,
                    selected = false
                )
            }

        }
    }
}
