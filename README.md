# PinLayout ⚬⚬⚬⚬
Android PinLayout for auth screens.

<img width="193" alt="Screen Shot 2022-12-04 at 03 42 09" src="https://user-images.githubusercontent.com/15641747/205468291-4aee9855-df47-4887-8ced-b075baf61931.png">


![Language](https://img.shields.io/github/languages/top/akndmr/PinLayout) 
![GitHub issues](https://img.shields.io/github/issues/akndmr/PinLayout)
![GitHub Repo stars](https://img.shields.io/github/stars/akndmr/PinLayout?style=social)
![JitPack](https://img.shields.io/jitpack/version/com.github.akndmr/PinLayout)

## Installation

Step 1. Add the JitPack repository to your build file

```gradle
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```gradle
  dependencies {
	    implementation 'com.github.akndmr:PinLayout:LatestVersion'
	}
```
    

## Demo
https://user-images.githubusercontent.com/15641747/205468372-c6af52be-6ebe-4691-b235-d2252ec1cb2a.mov

_Progressbar is for visual purposes; not part of PinLayout._



## Usage

#### Show a AirySnackbar

You can add pin by providing a data source like keyboard.
```kotlin
 addPinCode(code: String)
```

You can add whole pin from a clipboard.
```kotlin
 pasteFullPinCode(fullCode: String)
```


You can remove the last added pin.
```kotlin
 removePinCode()
```

You can clear PinLayout completely.
```kotlin
 clearPinCode()
```

You can register a listener for pin change and pin complete events.
```kotlin
 setOnPinChangeListener(
                    onPinChange = { pin ->
                        //todo
                    },
                    onPinComplete = {
                        //todo
                    }
                )
```

You can customize PinLayout with custom attributes. 
```kotlin
 app:pin_count="8"
 app:pin_view_active_color="@color/bg3"
 app:pin_view_default_color="@color/bg4"
 app:pin_view_gap="12dp"
 app:pin_view_radius="16dp"
 app:pin_view_extra_gap="@dimen/margin_32"
 app:pin_view_extra_gap_index="3"
```
You can use extra gap attribute to group pin views to fit with your UI requirements.

<img width="318" alt="with_gap_and_without_gap" src="https://user-images.githubusercontent.com/15641747/206033783-b325c327-c434-4cbd-ac2f-a38ce9905700.png">


## Roadmap

- Add custom animation support.

