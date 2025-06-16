package home.replace_me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a LinearLayout
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        linearLayout.gravity = android.view.Gravity.CENTER

        // Create an ImageView
        val imageView = ImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Set the icon to be displayed
        // Using @mipmap/ic_launcher to test the full adaptive icon
        imageView.setImageResource(R.mipmap.ic_launcher)

        // Add ImageView to LinearLayout
        linearLayout.addView(imageView)

        // Set the LinearLayout as the content view
        setContentView(linearLayout)
    }
}

// Note: We need to ensure R.mipmap.ic_launcher is the correct reference.
// Android Studio typically generates an R class that includes resources from mipmap.
// If R.mipmap.ic_launcher cannot be resolved, it might indicate a build/project configuration issue
// or that the R class isn't correctly generated/imported.
// For the purpose of this subtask, we assume R.mipmap.ic_launcher is resolvable.
// If not, an alternative like R.drawable.ic_launcher_foreground could be tried,
// though testing the adaptive icon is preferred.
