package com.akndmr.pinlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.akndmr.pinlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
        }

        with(binding) {
            buttonAddPin.setOnClickListener {
                val number = (1..9).random().toString()
                pinLayout1.addPinCode(number)
                pinLayout2.addPinCode(number)
                pinLayout3.addPinCode(number)
            }

            buttonRemovePin.setOnClickListener {
                pinLayout1.removePinCode()
                pinLayout2.removePinCode()
                pinLayout3.removePinCode()
            }

            pinLayout1.apply {
                setOnPinChangeListener(
                    onPinChange = { pin ->
                        progressBar1.progress = 100 * pin.length / totalPinCount()
                    },
                    onPinComplete = {}
                )
            }

            pinLayout2.apply {
                setOnPinChangeListener(
                    onPinChange = { pin ->
                        progressBar2.progress = 100 * pin.length / totalPinCount()

                    },
                    onPinComplete = {}
                )
            }

            pinLayout3.apply {
                setOnPinChangeListener(
                    onPinChange = { pin ->
                        progressBar3.progress = 100 * pin.length / totalPinCount()

                    },
                    onPinComplete = {}
                )
            }
        }
    }
}

fun PinLayout.totalPinCount(): Int {
    return children.filter { it is PinView }.count()
}