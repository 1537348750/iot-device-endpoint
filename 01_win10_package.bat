@echo off
setlocal

if not exist iot-device-web-package.zip (
  echo iot-device-web-package.zip not exists, skipping delete command
) else (
  del /s /q iot-device-web-package.zip
)

if not exist iot-device-web-package (
  mkdir iot-device-web-package
) else (
  echo iot-device-web-package already exists, skipping mkdir command
)

cd iot-device-web-package
if not exist lib (
  mkdir lib
) else (
  echo lib already exists, skipping mkdir command
)

xcopy ..\iot-device-web\target\lib\*.jar lib\
xcopy ..\iot-device-web\target\*.jar lib\

copy ..\start.sh .
copy ..\Dockerfile .

powershell Compress-Archive -Path .\* -DestinationPath ..\iot-device-web-package.zip

cd ..
rmdir /s /q iot-device-web-package

echo 记录输出日志和异常报错到win_package_log.txt文件
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
