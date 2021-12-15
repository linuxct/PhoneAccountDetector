package space.linuxct.phoneaccountdetector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView
    private lateinit var tvDetails: TextView
    private lateinit var icWarning: ImageView
    private lateinit var icCheckmark: ImageView
    private var appJustStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus = findViewById(R.id.tvStatus)
        tvDetails = findViewById(R.id.tvDetails)
        icWarning = findViewById(R.id.icWarning)
        icCheckmark = findViewById(R.id.icCheckmark)
        appJustStarted = true
        performVersionAndPermissionCheckThenUpdateView()
    }

    override fun onResume(){
        super.onResume()
        if (appJustStarted){
            appJustStarted = !appJustStarted
            return
        }
        performVersionAndPermissionCheckThenUpdateView()
    }

    private fun performVersionAndPermissionCheckThenUpdateView(){
        if (Build.VERSION.SDK_INT < 31){
            //use ReadPhoneState only
            if (arePermissionsGrantedOrRequest(arrayOf(Manifest.permission.READ_PHONE_STATE))){
                isAndroidVersionUnsupportedOrCheckStatusAndUpdateView()
            }
        } else {
            //Android 12 requires ReadPhoneState and ReadPhoneNumbers
            if (arePermissionsGrantedOrRequest(arrayOf(Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS))){
                isAndroidVersionUnsupportedOrCheckStatusAndUpdateView()
            }
        }
    }

    private fun arePermissionsGrantedOrRequest(permissions: Array<String>): Boolean {
        if (permissions.any {
                ContextCompat.checkSelfPermission(applicationContext, it) == PackageManager.PERMISSION_DENIED
            }) {
            ActivityCompat.requestPermissions(this@MainActivity, permissions, 1337)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            isAndroidVersionUnsupportedOrCheckStatusAndUpdateView()
        } else {
            Toast.makeText(applicationContext, "You need to grant the permission\nin order to check the list of apps using PhoneAccounts", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun isAndroidVersionUnsupportedOrCheckStatusAndUpdateView(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            updateViewAsUnsupported()
        } else {
            checkStatusAndUpdateView()
        }
    }

    private fun updateViewAsUnsupported() {
        icCheckmark.visibility = View.VISIBLE
        icWarning.visibility = View.INVISIBLE
        val spannableStatus = SpannableString("Your device is running an old version of Android that is not vulnerable to this bug.")
        spannableStatus.setSpan(StyleSpan(Typeface.BOLD), 0, spannableStatus.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvStatus.text = spannableStatus
        val spannableDetails = SpannableString("No apps were checked.")
        spannableDetails.setSpan(StyleSpan(Typeface.ITALIC), 0, spannableDetails.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvDetails.text = spannableDetails
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkStatusAndUpdateView(){
        val telecomManager = applicationContext.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        var dangerousAmountOfPhoneAccountsForPackage = false
        val resultDetails = SpannableStringBuilder()
        val accountHandleCollection = mutableListOf<PhoneAccountHandle>()
        accountHandleCollection.addAll(telecomManager.callCapablePhoneAccounts)
        accountHandleCollection.addAll(telecomManager.selfManagedPhoneAccounts)

        val groupedAccountHandles = accountHandleCollection.groupBy { it.componentName }
        for (accountHandleMap in groupedAccountHandles){
            val firstAccountHandle = accountHandleMap.value.first()
            val phoneAccountForFirstHandle = telecomManager.getPhoneAccount(firstAccountHandle)
            val activePhoneAccountsForPackageName = accountHandleMap.value.count()
            val accountsString = if (activePhoneAccountsForPackageName > 1) "accounts" else "account"
            val label = if (telecomManager.callCapablePhoneAccounts.contains(firstAccountHandle)){
                "Mobile Network (${phoneAccountForFirstHandle.label})"
            } else {
                phoneAccountForFirstHandle.label
            }

            val spannable = SpannableString("$label: $activePhoneAccountsForPackageName $accountsString\n\n")
            if (activePhoneAccountsForPackageName > 3){
                spannable.setSpan(StyleSpan(Typeface.BOLD), 0, spannable.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                dangerousAmountOfPhoneAccountsForPackage = true
            }
            resultDetails.append(spannable)
        }

        if (dangerousAmountOfPhoneAccountsForPackage) {
            icWarning.visibility = View.VISIBLE
            icCheckmark.visibility = View.GONE
            val spannable = SpannableString("At least an application may be abusing the PhoneAccounts bug. Please check below for details.")
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvStatus.text = spannable
        } else {
            icCheckmark.visibility = View.VISIBLE
            icWarning.visibility = View.GONE
            val spannable = SpannableString("No apps abusing the PhoneAccounts bug were found!")
            tvStatus.text = spannable
        }

        tvDetails.text = resultDetails.removeSuffix("\n\n")
    }


}