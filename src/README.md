
# Play Asset Delivery ANE - Source Code

The source code for our Play Asset Delivery ANE is provided under the MIT license.

To build this, please set up two environment variables:

* AIR_SDK_HOME - points to the root of your AIR SDK so that we can find mxmlc, adt etc.

* ANDROID_SDK_ROOT - points to your Android SDK e.g. ~/Library/Android/sdk

If you change into the 'src' folder and run Ant, the build should proceed and the ANE
will be copied to this folder. If packaging up the ANE for APM, this should then be copied
into the 'build' folder and then Ant can be run from the repository root.

Note that for the test package, the latest com.google.android.play.ane file will need to
be downloaded and placed into the 'ane' subfolder.

