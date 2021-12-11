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
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT < 31){
            //use ReadPhoneState only
            if (arePermissionsGrantedOrRequest(arrayOf(Manifest.permission.READ_PHONE_STATE))){
                checkStatusAndUpdateView()
            }
        } else {
            //Android 12 requires ReadPhoneState and ReadPhoneNumbers
            if (arePermissionsGrantedOrRequest(arrayOf(Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS))){
                checkStatusAndUpdateView()
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
            checkStatusAndUpdateView()
        } else {
            Toast.makeText(applicationContext, "You need to grant the permission\nin order to check the list of apps using PhoneAccounts", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkStatusAndUpdateView(){
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val telecomManager = applicationContext.getSystemService(Context.TELECOM_SERVICE) as TelecomManager

        var dangerousAmountOfPhoneAccountsForPackage = false
        var resultDetails = ""
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

            resultDetails += "$label: $activePhoneAccountsForPackageName $accountsString\n\n"
            if (activePhoneAccountsForPackageName > 3){
                dangerousAmountOfPhoneAccountsForPackage = true
            }
        }

        if (dangerousAmountOfPhoneAccountsForPackage) {
            findViewById<ImageView>(R.id.icWarning).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.icCheckmark).visibility = View.INVISIBLE
            val spannable = SpannableString("At least an application may be abusing the PhoneAccounts bug. Please check below for details.")
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvStatus.text = spannable
        } else {
            findViewById<ImageView>(R.id.icCheckmark).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.icWarning).visibility = View.INVISIBLE
            val spannable = SpannableString("No apps abusing the PhoneAccounts bug were found. Please check below for details.")
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvStatus.text = spannable
        }

        tvDetails.text = resultDetails
    }


}