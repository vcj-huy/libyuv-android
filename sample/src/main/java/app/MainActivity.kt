package app

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import android.widget.ImageView
import io.github.zncmn.libyuv.*
import java.io.ByteArrayOutputStream

/**
 * This activity demonstrates how to use JNI to encode and decode ogg/vorbis audio
 */
class MainActivity : Activity() {
    private lateinit var origin: ImageView
    private lateinit var convert: ImageView
    private lateinit var rotate90: ImageView
    private lateinit var mirror: ImageView
    private lateinit var origin_2: ImageView
    private lateinit var convert_2: ImageView
    private lateinit var rotate90_2: ImageView
    private lateinit var mirror_2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        origin = findViewById(R.id.origin)
        convert = findViewById(R.id.convert)
        rotate90 = findViewById(R.id.rotate90)
        mirror = findViewById(R.id.mirror)
        origin_2 = findViewById(R.id.origin_2)
        convert_2 = findViewById(R.id.convert_2)
        rotate90_2 = findViewById(R.id.rotate90_2)
        mirror_2 = findViewById(R.id.mirror_2)
    }

    override fun onResume() {
        super.onResume()

        val bitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888)
        val bitmap2 = Bitmap.createBitmap(1920, 1080, Bitmap.Config.RGB_565)
        val width = bitmap.width
        val height = bitmap.height
        val originalBuffer = AbgrBuffer.allocate(width, height)
        val original2Buffer = Rgb565Buffer.allocate(width, height)
        val i420Buffer = I420Buffer.allocate(width, height)
        val nv21Buffer = Nv21Buffer.allocate(width, height)
        val rotate90Buffer = I420Buffer.allocate(height, width)
        val nv21Rotate90Buffer = Nv21Buffer.allocate(height, width)
        val nv21MirrorBuffer = Nv21Buffer.allocate(height, width)

        // dummy draw
        Canvas(bitmap).also {
            it.drawRect(0f, 0f, 1920f, 1080f, Paint().also { p ->
                p.shader = LinearGradient(0f, 0f, 0f, 1080f, Color.GREEN, Color.YELLOW, Shader.TileMode.CLAMP)
            })
            it.drawRect(50f, 50f, 150f, 150f, Paint().also { p -> p.color = Color.BLACK })
        }
        bitmap.copyPixelsToBuffer(originalBuffer.bufferABGR)
        origin.setImageBitmap(bitmap)

        Canvas(bitmap2).also {
            it.drawRect(0f, 0f, 1920f, 1080f, Paint().also { p ->
                p.shader = LinearGradient(0f, 0f, 0f, 1080f, Color.GREEN, Color.YELLOW, Shader.TileMode.CLAMP)
            })
            it.drawRect(50f, 50f, 150f, 150f, Paint().also { p -> p.color = Color.BLACK })
        }
        bitmap2.copyPixelsToBuffer(original2Buffer.bufferRGB565)
        origin_2.setImageBitmap(bitmap2)

        originalBuffer.convertTo(nv21Buffer)
        nv21Buffer.rotate(nv21Rotate90Buffer, RotateMode.ROTATE_90)
        nv21Rotate90Buffer.mirrorTo(nv21MirrorBuffer)
        convert.setImageBitmap(yuvToBitmap(nv21Buffer, width, height))
        rotate90.setImageBitmap(yuvToBitmap(nv21Rotate90Buffer, height, width))
        mirror.setImageBitmap(yuvToBitmap(nv21MirrorBuffer, height, width))

        original2Buffer.convertTo(i420Buffer)
        i420Buffer.convertTo(nv21Buffer)
        nv21Buffer.rotate(nv21Rotate90Buffer, RotateMode.ROTATE_90)
        nv21Rotate90Buffer.mirrorTo(nv21MirrorBuffer)
        convert_2.setImageBitmap(yuvToBitmap(nv21Buffer, width, height))
        rotate90_2.setImageBitmap(yuvToBitmap(nv21Rotate90Buffer, height, width))
        mirror_2.setImageBitmap(yuvToBitmap(nv21MirrorBuffer, height, width))

        originalBuffer.release()
        rotate90Buffer.release()
        nv21Buffer.release()
        nv21Rotate90Buffer.release()
    }

    fun yuvToBitmap(nv21Buffer: Nv21Buffer, width: Int, height: Int): Bitmap {
        val yumData = nv21Buffer.asByteArray()
        val yuvImage = YuvImage(yumData, ImageFormat.NV21, width, height, null)
        val baos = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 80, baos)
        val jpegData: ByteArray = baos.toByteArray()

        return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.size)
    }
}
