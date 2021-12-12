# PhoneAccount Abuse Detector

#### Simple application to enumerate and detect any application that (ab)uses adding an indefinite amount of PhoneAccount(s) to Android's TelecomManager.  

## Background

This application exists because malicious or just improperly programmed applications can, intentionally or not, block your device from the ability to call emergency numbers. If you are in such a situation, this app helps you to find the culprit – which you then can uninstall (or disable).  

For the exact details on the vulnerability (like how it was discovered), please check the article by Mishaal Rahman [here](https://medium.com/@mmrahman123/how-a-bug-in-android-and-microsoft-teams-could-have-caused-this-users-911-call-to-fail-6525f9ba5e63).  

## Application usage

The application is very simple, and contains 2 components:
- A message at the top of the device, explaining if the application detected a possible abuse of this functionality which may cause issues while attempting to call Emergency Services.  
- A list of the applications that have registered a Phone Account in your device, usually including your own SIM Cards, Google Duo, Teams, among others.  
  Alongside each app, the number of accounts is displayed to facilitate the identification of the malfunctioning/hijacking application.

## Screenshots

To be added.
