package com.paulius.testapplication

enum class VolleyballVector(val resId: Int) {
    Rotate0(R.drawable.volleyball_0),
    Rotate20(R.drawable.volleyball_20),
    Rotate40(R.drawable.volleyball_40),
    Rotate60(R.drawable.volleyball_60),
    Rotate80(R.drawable.volleyball_80),
    Rotate100(R.drawable.volleyball_100),
    Rotate120(R.drawable.volleyball_120),
    Rotate140(R.drawable.volleyball_140),
    Rotate160(R.drawable.volleyball_160),
    Rotate180(R.drawable.volleyball_180),
    Rotate200(R.drawable.volleyball_200),
    Rotate220(R.drawable.volleyball_220),
    Rotate240(R.drawable.volleyball_240),
    Rotate260(R.drawable.volleyball_260),
    Rotate280(R.drawable.volleyball_280),
    Rotate300(R.drawable.volleyball_300),
    Rotate320(R.drawable.volleyball_320),
    Rotate340(R.drawable.volleyball_340)
}

fun VolleyballVector.next(): VolleyballVector {
    val values = enumValues<VolleyballVector>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

fun VolleyballVector.previous(): VolleyballVector {
    val values = enumValues<VolleyballVector>()
    val previousOrdinal = if (ordinal - 1 >= 0) {
        ordinal - 1
    } else {
        values.size - 1
    }
    return values[previousOrdinal]
}