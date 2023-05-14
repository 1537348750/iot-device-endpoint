@echo off
setlocal

echo This file is a win10 packaging script,
echo will build a compressed package called iot-device-web-package.zip for building Docker images.
echo Please note: Don't include Chinese or other special characters in this file, otherwise errors may occur during operation.

if not exist iot-device-web-package.zip (
  echo iot-device-web-package.zip not exists
) else (
  del /s /q iot-device-web-package.zip
)

if not exist iot-device-web-package (
  mkdir iot-device-web-package
) else (
  echo iot-device-web-package already exists
)

cd iot-device-web-package
if not exist lib (
  mkdir lib
) else (
  echo lib already exists, skipping mkdir command
)

if not exist cert (
  mkdir cert
) else (
  echo cert already exists, skipping mkdir command
)

echo xcopy ..\iot-device-web\target\lib\*.jar lib\
xcopy ..\iot-device-web\target\*.jar lib\
xcopy ..\iot-device-sdk\src\main\resources\*.jks cert\


copy ..\script\start.sh .
copy ..\script\Dockerfile .
copy ..\script\run_container.sh .

powershell Compress-Archive -Path .\* -DestinationPath ..\iot-device-web-package.zip

cd ..
rmdir /s /q iot-device-web-package

echo Record the output log and exception error to the win package log.txt file
goto :end

:error
echo An error occurred, check win_package_log.txt for details
goto :end

:end
if %errorlevel% neq 0 (
  echo %date% %time% - Failed with error code %errorlevel%. >> win_package_log.txt
) else (
  echo %date% %time% - Succeeded. >> win_package_log.txt
)

exit /b %errorlevel%
