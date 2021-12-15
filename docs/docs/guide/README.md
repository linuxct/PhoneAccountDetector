## Background

This application exists because malicious or just improperly programmed applications can, intentionally or not, block your device from the ability to call emergency numbers. If you are in such a situation, this app helps you to find the culprit – which you then can uninstall (or disable).  

For the exact details on the vulnerability (why this happens, how it was discovered, fixes timeline, ...), please check the article by Mishaal Rahman [here](https://medium.com/@mmrahman123/how-a-bug-in-android-and-microsoft-teams-could-have-caused-this-users-911-call-to-fail-6525f9ba5e63).  

## About permissions

This application requires two call management permissions:

- [Manifest.permission.READ_PHONE_STATE](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_STATE)
- [Manifest.permission.READ_PHONE_NUMBERS](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_NUMBERS).  

This is because on Android, in order to read which applications are adding PhoneAccounts to Android's TelecomManager, these permissions are necessary.  

Permission `READ_PHONE_STATE` is used in all supported Android versions, whereas `READ_PHONE_NUMBERS` is requested on Android 12 and onwards exclusively.

No permission is (ab)used to log, collect or process any personally identifiable user information.

## Application usage

The application is very simple, and contains 2 components:
- A message at the top of the device, explaining if the application detected a possible abuse of this functionality which may cause issues while attempting to call Emergency Services.  
- A list of the applications that have registered a Phone Account in your device, usually including your own SIM Cards, Google Duo, Teams, among others. Alongside each app, the number of accounts is displayed to facilitate the identification of the malfunctioning/hijacking application.  


Check this video if you have doubts on how to interpret this data:
<p align="center">
  <a target="_blank" href="https://www.youtube.com/embed/MT9Xs01Zwa0?ecver=1&autoplay=1&cc_load_policy=1&iv_load_policy=3&rel=0&yt:stretch=16:9&autohide=1&color=red">
    <img src="https://i.imgur.com/IJYA3oo.png" alt="Watch on YouTube" />
  </a><br/>

> (Thanks to <a href="https://www.youtube.com/c/AndroidExplainedTips">Explaining Android</a> for the video)
</p>

## Screenshots

#### Permissions necessary for the app to work:
<p align="center" width="100%">
    <img width="30%" src="https://i.imgur.com/gDhJHeb.png" alt="Permission management" />
</p>

#### Case where Teams added 4 PhoneAccounts to TelecomManager:
<p align="center" width="100%">
    <img width="30%" src="https://i.imgur.com/I4zSf8R.png" alt="Abnormal case" /><br/>
</p>

> The app flags this as abnormal behaviour

#### Case without any abnormal app behaviour"
<p align="center" width="100%">
    <img width="30%" src="https://i.imgur.com/tsKHKTT.png" alt="Normal case" /><br/>
</p>

## Building the app

You can use Android Studio to build the application, or you can build it by using the CLI.  

Navigate to the folder where the source code is located:  
```cd /path/where/you/downloaded/PhoneAccountDetector/```  

Then, check that Gradle runs properly by executing:  
For Linux/MacOS: `./gradlew tasks`  
For Windows: `gradlew tasks`  

You can now build the application in release or debug flavor:   
`./gradlew assemble`  

After it's done building, you will now need to sign the resulting APK by using apksigner, or jarsigner. Here's an example:  
```apksigner sign --ks /path/to/example.keystore --ks-pass pass:"EXAMPLEPASSWORD" --v1-signing-enabled true --v2-signing-enabled true --verity-enabled true *.apk```  
