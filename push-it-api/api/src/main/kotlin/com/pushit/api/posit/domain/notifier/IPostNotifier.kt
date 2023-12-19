package com.pushit.api.posit.domain.notifier

import com.pushit.api.posit.domain.model.Posit

fun interface IPostNotifier {
    fun notifyNewPost(post: Posit)
}