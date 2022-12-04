package com.akndmr.pinlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import android.widget.Space
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children

class PinLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var pinItemCount = DEFAULT_PIN_ITEM_COUNT
    private var pinItemsGap = context.resources.getDimensionPixelSize(R.dimen.pin_items_gap)
    private var onPinChange: ((String) -> Unit)? = null
    private var onPinComplete: ((String) -> Unit)? = null
    private var pinViewRadius: Float = INVALID_RADIUS
    private var pinViewExtraGapIndex: Int = INVALID
    private var pinViewExtraGap = pinItemsGap

    @ColorInt
    private var pinViewActiveColor = ContextCompat.getColor(context, R.color.white)

    @ColorInt
    private var pinViewDefaultColor = ContextCompat.getColor(context, R.color.blublu)

    private var pinText = INITIAL_PIN_TEXT

    init {
        orientation = HORIZONTAL
        layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f }

        context.obtainStyledAttributes(attrs, R.styleable.PinLayout).use { arr ->
            pinItemCount = arr.getInt(R.styleable.PinLayout_pin_count, DEFAULT_PIN_ITEM_COUNT)
            pinViewRadius = arr.getDimension(R.styleable.PinLayout_pin_view_radius, INVALID_RADIUS)
            pinItemsGap =
                arr.getDimension(R.styleable.PinLayout_pin_view_gap, pinItemsGap.toFloat()).toInt()
            pinViewExtraGapIndex =
                arr.getInt(R.styleable.PinLayout_pin_view_extra_gap_index, INVALID)
            pinViewExtraGap =
                arr.getDimension(R.styleable.PinLayout_pin_view_extra_gap, pinItemsGap.toFloat())
                    .toInt()
            pinViewActiveColor =
                arr.getColor(R.styleable.PinLayout_pin_view_active_color, pinViewActiveColor)
            pinViewDefaultColor =
                arr.getColor(R.styleable.PinLayout_pin_view_default_color, pinViewDefaultColor)
        }

        MutableList(pinItemCount) { index ->
            addView(PinView(context).apply {
                if (pinViewRadius != INVALID_RADIUS) {
                    radius = pinViewRadius
                }
                colorActive = pinViewActiveColor
                colorDefault = pinViewDefaultColor
            })
            if (index < pinItemCount - 1) {
                if (pinViewExtraGapIndex != INVALID && index == pinViewExtraGapIndex) {
                    addView(Space(context).apply { minimumWidth = pinViewExtraGap })
                } else {
                    addView(Space(context).apply { minimumWidth = pinItemsGap })
                }
            }
        }
    }

    fun addPinCode(code: String) {
        if (pinText.length < pinItemCount) {
            pinText += code

            (children.filter { it is PinView }
                .elementAt(pinText.length - 1) as PinView).apply { toggle() }

            onPinChange?.invoke(pinText)

            if (pinText.length == pinItemCount) {
                onPinComplete?.invoke(pinText)
            }
        } else {
            Log.w(
                javaClass.simpleName,
                "Pin code entered ignored since it exceeds defined pin code size:{$pinItemCount}"
            )
        }
    }

    fun pasteFullPinCode(fullCode: String) {
        if (fullCode.length != pinItemCount) return

        clearPinCode()
        pinText = INITIAL_PIN_TEXT
        fullCode.forEach { pin ->
            addPinCode(pin.toString())
        }
    }

    fun removePinCode() {
        if (pinText.length in 1..pinItemCount) {
            (children.filter { it is PinView }
                .elementAt(pinText.length - 1) as PinView).apply { toggle() }
            pinText = pinText.dropLast(1)
            onPinChange?.invoke(pinText)
        }
    }

    fun clearPinCode() {
        if (pinText.isNotEmpty()) {
            children.toList().filterIsInstance<PinView>().filter { it.isActive() }
                .map { it.toggle() }
            pinText = INITIAL_PIN_TEXT
            onPinChange?.invoke(pinText)
        }
    }

    fun setOnPinChangeListener(
        onPinChange: (String) -> Unit,
        onPinComplete: ((String) -> Unit)? = null
    ) {
        this.onPinChange = onPinChange
        this.onPinComplete = onPinComplete
    }

    companion object {
        const val DEFAULT_PIN_ITEM_COUNT = 4
        const val INITIAL_PIN_TEXT = ""
        private const val INVALID_RADIUS = -1f
        private const val INVALID = -1
    }
}