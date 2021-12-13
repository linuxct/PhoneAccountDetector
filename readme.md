# PhoneAccount Abuse Detector<br/> [![Latest Version](https://img.shields.io/github/v/release/linuxct/PhoneAccountDetector)](https://github.com/linuxct/PhoneAccountDetector/releases/latest) ![Compatibility](https://img.shields.io/badge/compat-API%2029%2B-brightgreen)

**Simple application to enumerate and detect any application that (ab)uses adding an indefinite amount of PhoneAccount(s) to Android's TelecomManager.**  

## Background

This application exists because malicious or just improperly programmed applications can, intentionally or not, block your device from the ability to call emergency numbers. If you are in such a situation, this app helps you to find the culprit – which you then can uninstall (or disable).  

For the exact details on the vulnerability (why this happens, how it was discovered, fixes timeline, ...), please check the article by Mishaal Rahman [here](https://medium.com/@mmrahman123/how-a-bug-in-android-and-microsoft-teams-could-have-caused-this-users-911-call-to-fail-6525f9ba5e63).  

## Application usage

The application is very simple, and contains 2 components:
- A message at the top of the device, explaining if the application detected a possible abuse of this functionality which may cause issues while attempting to call Emergency Services.  
- A list of the applications that have registered a Phone Account in your device, usually including your own SIM Cards, Google Duo, Teams, among others. Alongside each app, the number of accounts is displayed to facilitate the identification of the malfunctioning/hijacking application.

## About permissions

This application requires two call management permissions, [Manifest.permission.READ_PHONE_STATE](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_STATE) and [Manifest.permission.READ_PHONE_NUMBERS](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_NUMBERS).  

READ_PHONE_STATE is used in all supported Android versions, whereas READ_PHONE_NUMBERS is requested on Android 12 and onwards exclusively. This is because on Android, in order to read which applications are adding PhoneAccounts to Android's TelecomManager, these permissions are necessary.  

No permission is (ab)used to log, collect or process any personally identifiable user information.

## Screenshots

<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/ZS7OOIf.png" alt="Permission management"></img><br/>
  Permissions necessary for the app to work
</p>
<br/>
<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/ptKrgxe.png" alt="Abnormal case"></img><br/>
  Case where Teams added 380 PhoneAccounts to TelecomManager<br/>
  The app flags this as abnormal behaviour<br/>
</p>
<br/>
<p align="center" width="100%">
  <img width="30%" src="https://i.imgur.com/q6UL0tl.png" alt="Normal case"></img><br/>
  Case without any abnormal app behaviour
</p>
