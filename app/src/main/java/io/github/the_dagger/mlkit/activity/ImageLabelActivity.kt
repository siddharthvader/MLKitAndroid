package io.github.the_dagger.mlkit.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import io.github.the_dagger.mlkit.R
import io.github.the_dagger.mlkit.adapter.ImageLabelAdapter
import io.github.the_dagger.mlkit.adapter.RecycleLabel
import io.github.the_dagger.mlkit.adapter.RecycleLabelAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_image_label.*
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import io.github.the_dagger.mlkit.adapter.BinLabelProcessor


class ImageLabelActivity : BaseCameraActivity() {

    private var itemsList: ArrayList<Any> = ArrayList()
    private lateinit var itemAdapter: RecycleLabelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheet(R.layout.layout_image_label)
        rvLabel.layoutManager = LinearLayoutManager(this)
    }

    private fun getLabelsFromDevice(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionLabelDetector
        itemsList.clear()
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    fabProgressCircle.hide()
                    for(item in it){
                        itemsList.add(RecycleLabel(item))
                    }

                    val processor = BinLabelProcessor(itemsList)
                    val items = processor.labels
                    itemAdapter = RecycleLabelAdapter(items, false)
                    rvLabel.adapter = itemAdapter
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    fabProgressCircle.hide()
                    Toast.makeText(baseContext,"Sorry, something went wrong!",Toast.LENGTH_SHORT).show()
                }
    }

    private fun getLabelsFromCloud(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .visionCloudLabelDetector
        itemsList.clear()
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    fabProgressCircle.hide()
                    itemsList.addAll(it)
//                    itemAdapter = ImageLabelAdapter(itemsList, true)
                    itemAdapter = RecycleLabelAdapter(itemsList, true)
                    rvLabel.adapter = itemAdapter
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    fabProgressCircle.hide()
                    Toast.makeText(baseContext,"Sorry, something went wrong!",Toast.LENGTH_SHORT).show()
                }
    }

    override fun onClick(v: View?) {
        fabProgressCircle.show()
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            getLabelsFromDevice(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

}
