package com.jamieadkins.acnh.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToComposite(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)
    return this
}