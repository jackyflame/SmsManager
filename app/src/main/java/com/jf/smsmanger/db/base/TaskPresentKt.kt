package com.jf.smsmanger.db.base

import com.jf.baselibraray.db.BasePresent
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class TaskPresentKt: BasePresent() {

    fun <T> callTask(task:Rxtask<T>,callback: TaskCallback<T>?){
        Observable.create(ObservableOnSubscribe<T> {
            it.onNext(task.runTask())
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(object: Observer<T> {
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {
                callback?.onStart()
            }
            override fun onNext(t: T) {
                callback?.onSuccess(t)
            }
            override fun onError(e: Throwable) {
                callback?.onError(e)
            }
        })
    }

}