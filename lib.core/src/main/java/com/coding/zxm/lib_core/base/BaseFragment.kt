package com.coding.zxm.lib_core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coding.zxm.lib_core.R
import com.zxm.utils.core.bar.StatusBarCompat

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 */
abstract class BaseFragment() : Fragment(), FragmentLazyLifecycleOwner.Callback {

    protected val TAG = this.javaClass.simpleName

    var mContext: Context? = null

    protected var rootView: View? = null

    //lifecycle
    private var mLazyViewLifecycleOwner: FragmentLazyLifecycleOwner? = null

    //animation
    private var mCalled = true
    private var mEnterAnimationStatus: Int =
        ANIMATION_ENTER_STATUS_NOT_START
    private val isInEnterAnimationLiveData = MutableLiveData<Boolean>()

    //back press

    companion object {

        val SLIDE_TRANSITION_CONFIG: TransitionConfig =
            TransitionConfig(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
            )

        val SCALE_TRANSITION_CONFIG: TransitionConfig =
            TransitionConfig(
                R.anim.scale_enter, R.anim.slide_still,
                R.anim.slide_still, R.anim.scale_exit
            )

        const val ANIMATION_ENTER_STATUS_NOT_START = -1
        const val ANIMATION_ENTER_STATUS_STARTED = 0
        const val ANIMATION_ENTER_STATUS_END = 1
    }

    abstract fun setLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(setLayoutId(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLazyViewLifecycleOwner = FragmentLazyLifecycleOwner(this)
        mLazyViewLifecycleOwner?.let {
            viewLifecycleOwner.lifecycle.addObserver(it)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isInEnterAnimationLiveData.postValue(false)

        initParamsAndValues()

        rootView?.let {
            initViews(it)
        }

    }

    protected fun setStatusBarColorNoTranslucent(@ColorRes colorRes: Int) {
        activity?.let {
            StatusBarCompat.setColorNoTranslucent(
                activity,
                resources.getColor(colorRes)
            )
//            StatusBarCompat.setDarkMode(it)
        }
    }

    abstract fun initParamsAndValues()

    abstract fun initViews(rootView: View)

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (!enter) {
            // This is a workaround for the bug where child value disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            var rootParentFragment: Fragment? = null
            var parentFragment = parentFragment
            while (parentFragment != null) {
                rootParentFragment = parentFragment
                parentFragment = parentFragment.parentFragment
            }
            if (rootParentFragment != null && rootParentFragment.isRemoving) {
                val doNothingAnim: Animation = AlphaAnimation(1f, 1f)
                val duration = resources.getInteger(R.integer.qmui_anim_duration)
                doNothingAnim.duration = duration.toLong()
                return doNothingAnim
            }
        }
        var animation: Animation? = null
        if (enter) {
            try {
                animation = AnimationUtils.loadAnimation(context, nextAnim)
            } catch (ignored: Throwable) {
            }
            if (animation != null) {
                animation.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        checkAndCallOnEnterAnimationStart(animation)
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        checkAndCallOnEnterAnimationEnd(animation)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            } else {
                checkAndCallOnEnterAnimationStart(null)
                checkAndCallOnEnterAnimationEnd(null)
            }
        }
        return animation
    }


    fun isAttachedToActivity(): Boolean {
        return !isRemoving && rootView != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        notifyFragmentVisibleToUserChanged(isVisibleToUser && isParentVisibleToUser())
    }

    override fun isVisibleToUser(): Boolean {
        return userVisibleHint && isParentVisibleToUser()
    }

    /**
     * @return true if parentFragments is visible to user
     */
    private fun isParentVisibleToUser(): Boolean {
        var parentFragment = parentFragment
        while (parentFragment != null) {
            if (!parentFragment.userVisibleHint) {
                return false
            }
            parentFragment = parentFragment.parentFragment
        }
        return true
    }

    private fun notifyFragmentVisibleToUserChanged(isVisibleToUser: Boolean) {
        if (mLazyViewLifecycleOwner != null) {
            mLazyViewLifecycleOwner!!.setViewVisible(isVisibleToUser)
        }
        if (isAdded) {
            val childFragments = childFragmentManager.fragments
            for (fragment in childFragments) {
                if (fragment is BaseFragment) {
                    (fragment as BaseFragment).notifyFragmentVisibleToUserChanged(
                        isVisibleToUser && fragment.userVisibleHint
                    )
                }
            }
        }
    }

    //======================================Animation===========================================//

    private fun checkAndCallOnEnterAnimationStart(animation: Animation?) {
        mCalled = false
        onEnterAnimationStart(animation)
        if (!mCalled) {
            throw RuntimeException(javaClass.simpleName + " did not call through to super.onEnterAnimationStart(Animation)")
        }
    }

    private fun checkAndCallOnEnterAnimationEnd(animation: Animation?) {
        mCalled = false
        onEnterAnimationEnd(animation)
        if (!mCalled) {
            throw RuntimeException(javaClass.simpleName + " did not call through to super.onEnterAnimationEnd(Animation)")
        }
    }

    protected fun onEnterAnimationStart(animation: Animation?) {
        if (mCalled) {
            throw IllegalAccessError("don't call #onEnterAnimationStart() directly")
        }
        mCalled = true
        mEnterAnimationStatus = ANIMATION_ENTER_STATUS_STARTED
        isInEnterAnimationLiveData.setValue(true)
    }

    protected fun onEnterAnimationEnd(animation: Animation?) {
        if (mCalled) {
            throw IllegalAccessError("don't call #onEnterAnimationEnd() directly")
        }
        mCalled = true
        mEnterAnimationStatus = ANIMATION_ENTER_STATUS_END
        isInEnterAnimationLiveData.setValue(false)
    }

    protected fun <T> enterAnimationAvoidTransform(origin: LiveData<T>?): LiveData<T>? {
        return enterAnimationAvoidTransform(origin, isInEnterAnimationLiveData)
    }

    protected fun <T> enterAnimationAvoidTransform(
        origin: LiveData<T>?,
        enterAnimationLiveData: LiveData<Boolean>?
    ): LiveData<T>? {
        val result = MediatorLiveData<T>()
        result.addSource(enterAnimationLiveData!!, object : Observer<Boolean> {
            var isAdded = false
            override fun onChanged(isInEnterAnimation: Boolean) {
                if (isInEnterAnimation) {
                    isAdded = false
                    result.removeSource(origin!!)
                } else {
                    if (!isAdded) {
                        isAdded = true
                        result.addSource(
                            origin!!
                        ) { t -> result.value = t }
                    }
                }
            }
        })
        return result
    }
}

/**
 * Specific animation resources to run for the fragments that are
 * entering and exiting in this transaction.The <code>popEnter</code>
 * and <code>popExit</code> animations will be played for enter/exit
 * operations specifically when popping the back stack.
 *
 * <pre class="prettyprint">
 *  fragmentManager.beingTransaction()
 *      .setCustomAnimations(enter1, exit1, popEnter1, popExit1)
 *      .add(MyFragmentClass, args, tag1) // this fragment gets the first animations
 *      .setCustomAnimations(enter2, exit2, popEnter2, popExit2)
 *      .add(MyFragmentClass, args, tag2) // this fragment gets the second animations
 *      .commit()
 * </pre>
 */
class TransitionConfig(val enter: Int, val exit: Int, val popEnter: Int, val popExit: Int) {
    constructor(enter: Int, popExit: Int) : this(enter, 0, 0, popExit)
}