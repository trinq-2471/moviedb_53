package com.sun.moviedb_53.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

abstract class BaseFragment : Fragment() {

    val TAG = BaseFragment::class.java.simpleName

    abstract fun getLayoutId(): Int

    private var weakReference: WeakReference<Activity>? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReference = WeakReference<Activity>(activity)
        alertDialog = getBaseActivity()?.let {
            AlertDialog.Builder(it)
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ -> dialog.cancel() }.create()
        }
        onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(view)
        onViewCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let {
            it.isClickable = true
        }
    }

    fun getBaseActivity(): Activity? = weakReference?.get()

    open fun onViewCreated() {
        onInit()
        onEvent()
    }

    abstract fun onViewCreated(view: View)

    open fun onInit() {}

    open fun onEvent() {}

    open fun onCreate() {}

    protected fun showSingleAlertDialog(message: String) {
        alertDialog?.let {
            if (it.isShowing) {
                it.setMessage(message)
                it.show()
            }
        }
    }

    protected fun showAlertDialog(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    protected fun showAlertDialog(context: Context, @StringRes resId: Int) {
        showAlertDialog(context, context.resources.getString(resId))
    }
}
