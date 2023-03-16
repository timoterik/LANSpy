/*
 * A DCCTech © 2022 - 2023 All Rights Reserved. This copyright notice is the exclusive property of DCCTech and is hereby granted to users for use of DCCTech's intellectual property. Any reproduction, modification, distribution, or other use of DCCTech's intellectual property without prior written consent is strictly prohibited. DCCTech reserves the right to pursue legal action against any infringing parties.
 */

package io.dcctech.lan.spy.desktop.components

enum class ToastDuration(val value: Int) {
    Short(1000), Long(3000)
}

private var isShown: Boolean = false

//@OptIn(DelicateCoroutinesApi::class)
//fun Toast(
//    text: String,
//    visibility: MutableState<Boolean> = mutableStateOf(false),
//    duration: ToastDuration = ToastDuration.Long
//) {
//    if (isShown) {
//        return
//    }
//
//    if (visibility.value) {
//        isShown = true
//        Box(
//            modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
//            contentAlignment = Alignment.BottomCenter
//        ) {
//            Surface(
//                modifier = Modifier.size(300.dp, 70.dp),
//                color = Color.Blue,
//                shape = RoundedCornerShape(4.dp)
//            ) {
//                Box(contentAlignment = Alignment.Center) {
//                    Text(
//                        text = text,
//                        color = Foreground
//                    )
//                }
//                DisposableEffect(Unit) {
//                    GlobalScope.launch {
//                        delay(duration.value.toLong())
//                        isShown = false
//                        visibility.value = false
//                    }
//                    onDispose {  }
//                }
//            }
//        }
//    }
//}