###### 2024.04.09 [v1.3.0]

```
Defensive programming in status event handling to cope with spurious asset updated events.
Updating implementation of showCellularDataConfirmation to use the new API for this (showConfirmationDialog) - no change to the AS3 API.
Switching to imports of per-feature Play Asset Delivery library rather than the original Play Core library; and updating the imports for Tasks using Android GMS packages rather than Android Play Core. 
```

###### 2024.01.16 [v1.2.0]

```
Updating internal architecture to allow multiple ANE instances to be created within an app
Updating 'removeAssetPack' functionality to return 'pending' if the task is successfully started and dispatching 'not_installed' when it succeeds
Adding 'debugMode' property to the ANE to enable/disable release-mode debugging of the Java code via logcat
```

###### 2022.01.21 [v1.1.0]

```
Add showCellularDataConfirmation function for handling WAITING_FOR_WIFI (https://github.com/airsdk/Adobe-Runtime-Support/discussions/1581)
```

###### 2022.01.20 [v1.0.2]

```
Update ANE with fixes for https://github.com/airsdk/Adobe-Runtime-Support/issues/1538
```

###### 2021.12.06 [v1.0.1]

```
Update to latest ANE release
```

###### 2021.12.06 [v1.0.0]

```
Update readme
```

###### 2021.12.06 [v1.0.0]

```
Initial release
```

