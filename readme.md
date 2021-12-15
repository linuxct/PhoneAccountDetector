<p align="center" width="100%">
  <img src="https://github.com/linuxct/PhoneAccountDetector/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png?raw=true" alt="logo"></img><br/>
</p>

# PhoneAccount Abuse Detector<br/> [![Latest Version](https://img.shields.io/github/v/release/linuxct/PhoneAccountDetector)](https://github.com/linuxct/PhoneAccountDetector/releases/latest) ![Compatibility](https://img.shields.io/badge/compat-API%2029%2B-brightgreen)

**Simple application to enumerate and detect any application that (ab)uses adding an indefinite amount of PhoneAccount(s) to Android's TelecomManager.** As seen on [Android Police](https://www.androidpolice.com/heres-a-way-to-find-out-if-911-calls-on-your-android-phone-might-fail/), [XDA-Developers](https://www.xda-developers.com/avoid-android-emergency-calling-bug-this-app/), [Xataka Android](https://www.xatakandroid.com/aplicaciones-android/descubre-tu-movil-puede-bloquearse-llamando-a-emergencias-esta-app-te-dice).  

## Background

This application exists because malicious or just improperly programmed applications can, intentionally or not, block your device from the ability to call emergency numbers. If you are in such a situation, this app helps you to find the culprit – which you then can uninstall (or disable).  

For the exact details on the vulnerability (why this happens, how it was discovered, fixes timeline, ...), please check the article by Mishaal Rahman [here](https://medium.com/@mmrahman123/how-a-bug-in-android-and-microsoft-teams-could-have-caused-this-users-911-call-to-fail-6525f9ba5e63).  

## About permissions

This application requires two call management permissions, [Manifest.permission.READ_PHONE_STATE](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_STATE) and [Manifest.permission.READ_PHONE_NUMBERS](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_NUMBERS).  

READ_PHONE_STATE is used in all supported Android versions, whereas READ_PHONE_NUMBERS is requested on Android 12 and onwards exclusively. This is because on Android, in order to read which applications are adding PhoneAccounts to Android's TelecomManager, these permissions are necessary.  

No permission is (ab)used to log, collect or process any personally identifiable user information.

## Download

<p align="center">
  <a href="https://github.com/linuxct/PhoneAccountDetector/releases/latest/download/app-release.apk">
    <img src="https://i.imgur.com/eKVKAIk.png" alt="Download button"></img><br/>
    Click here to download the latest version
  </a>
</p>

<p align="center">
  <a href="https://play.google.com/store/apps/details?id=space.linuxct.phoneaccountdetector">
    <img src="https://i.imgur.com/RvsPBjV.png" alt="Download button"></img><br/>
    Click here to download from Google Play<br/>(usually a few versions behind GitHub, so GitHub is recommended)
  </a>
</p>

## Application usage

The application is very simple, and contains 2 components:
- A message at the top of the device, explaining if the application detected a possible abuse of this functionality which may cause issues while attempting to call Emergency Services.  
- A list of the applications that have registered a Phone Account in your device, usually including your own SIM Cards, Google Duo, Teams, among others. Alongside each app, the number of accounts is displayed to facilitate the identification of the malfunctioning/hijacking application.  
<br/>
<p align="center">
  Check this video if you have doubts on how to interpret this data:<br/><br/>
  <a target="_blank" href="https://www.youtube.com/embed/MT9Xs01Zwa0?ecver=1&autoplay=1&cc_load_policy=1&iv_load_policy=3&rel=0&yt:stretch=16:9&autohide=1&color=red">
    <img src="https://i.imgur.com/IJYA3oo.png" alt="Watch on YouTube"></img>
  </a><br/>
  (Thanks to <a href="https://www.youtube.com/c/AndroidExplainedTips">Explaining Android</a> for the video)
</p>

## Screenshots

<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/gDhJHeb.png" alt="Permission management"></img><br/>
  Permissions necessary for the app to work
</p>
<br/>
<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/I4zSf8R.png" alt="Abnormal case"></img><br/>
  Case where Teams added 4 PhoneAccounts to TelecomManager<br/>
  The app flags this as abnormal behaviour<br/>
</p>
<br/>
<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/tsKHKTT.png" alt="Normal case"></img><br/>
  Case without any abnormal app behaviour
</p>
