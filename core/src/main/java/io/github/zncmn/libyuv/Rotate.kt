package io.github.zncmn.libyuv

import kotlin.math.min

fun H420Buffer.rotate(dst: H420Buffer, rotateMode: RotateMode) {
    Yuv.rotateI420Rotate(bufferY, strideY, bufferU, strideU, bufferV, strideV,
        dst.bufferY, dst.strideY, dst.bufferU, dst.strideU, dst.bufferV, dst.strideV,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun I400Buffer.rotate(dst: I400Buffer, rotateMode: RotateMode) {
    Yuv.rotateRotatePlane(bufferY, strideY, dst.bufferY, dst.strideY,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun I420Buffer.rotate(dst: I420Buffer, rotateMode: RotateMode) {
    Yuv.rotateI420Rotate(bufferY, strideY, bufferU, strideU, bufferV, strideV,
        dst.bufferY, dst.strideY, dst.bufferU, dst.strideU, dst.bufferV, dst.strideV,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun I444Buffer.rotate(dst: I444Buffer, rotateMode: RotateMode) {
    Yuv.rotateI444Rotate(bufferY, strideY, bufferU, strideU, bufferV, strideV,
        dst.bufferY, dst.strideY, dst.bufferU, dst.strideU, dst.bufferV, dst.strideV,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun J400Buffer.rotate(dst: J400Buffer, rotateMode: RotateMode) {
    Yuv.rotateRotatePlane(bufferY, strideY, dst.bufferY, dst.strideY,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun J420Buffer.rotate(dst: J420Buffer, rotateMode: RotateMode) {
    Yuv.rotateI420Rotate(bufferY, strideY, bufferU, strideU, bufferV, strideV,
        dst.bufferY, dst.strideY, dst.bufferU, dst.strideU, dst.bufferV, dst.strideV,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun Nv12Buffer.rotate(dst: I420Buffer, rotateMode: RotateMode) {
    Yuv.rotateNV12ToI420Rotate(bufferY, strideY, bufferUV, strideUV,
        dst.bufferY, dst.strideY, dst.bufferU, dst.strideU, dst.bufferV, dst.strideV,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun Nv12Buffer.rotate(dst: Nv12Buffer, rotateMode: RotateMode) {
    Yuv.rotateNV12Rotate(bufferY, strideY, bufferUV, strideUV,
            dst.bufferY, dst.strideY, dst.bufferUV, dst.strideUV,
            calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun Nv21Buffer.rotate(dst: I420Buffer, rotateMode: RotateMode) {
    Yuv.rotateNV12ToI420Rotate(bufferY, strideY, bufferVU, strideVU,
            dst.bufferY, dst.strideY, dst.bufferV, dst.strideV, dst.bufferU, dst.strideU,
            calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun Nv21Buffer.rotate(dst: Nv21Buffer, rotateMode: RotateMode) {
    Yuv.rotateNV21Rotate(bufferY, strideY, bufferVU, strideVU,
        dst.bufferY, dst.strideY, dst.bufferVU, dst.strideVU,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun AbgrBuffer.rotate(dst: AbgrBuffer, rotateMode: RotateMode) {
    Yuv.rotateARGBRotate(bufferABGR, strideABGR, dst.bufferABGR, dst.strideABGR,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun ArgbBuffer.rotate(dst: ArgbBuffer, rotateMode: RotateMode) {
    Yuv.rotateARGBRotate(bufferARGB, strideARGB, dst.bufferARGB, dst.strideARGB,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun BgraBuffer.rotate(dst: BgraBuffer, rotateMode: RotateMode) {
    Yuv.rotateARGBRotate(bufferBGRA, strideBGRA, dst.bufferBGRA, dst.strideBGRA,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

fun RgbaBuffer.rotate(dst: RgbaBuffer, rotateMode: RotateMode) {
    Yuv.rotateARGBRotate(bufferRGBA, strideRGBA, dst.bufferRGBA, dst.strideRGBA,
        calculateWidth(this, dst, rotateMode), calculateHeight(this, dst, rotateMode), rotateMode.degrees)
}

private fun calculateWidth(src: Buffer, dst: Buffer, rotateMode: RotateMode): Int {
    return when (rotateMode) {
        RotateMode.ROTATE_0, RotateMode.ROTATE_180 -> min(src.width, dst.width)
        RotateMode.ROTATE_90, RotateMode.ROTATE_270 -> min(src.width, dst.height)
    }
}

private fun calculateHeight(src: Buffer, dst: Buffer, rotateMode: RotateMode): Int {
    return when (rotateMode) {
        RotateMode.ROTATE_0, RotateMode.ROTATE_180 -> min(src.height, dst.height)
        RotateMode.ROTATE_90, RotateMode.ROTATE_270 -> min(src.height, dst.width)
    }
}