@echo off
setlocal

rem 1. 在当前目录新建个文件夹叫做iot-device-endpoint-package
if not exist iot-device-endpoint-package (
  mkdir iot-device-endpoint-package
) else (
  echo iot-device-endpoint-package already exists, skipping mkdir command
)

rem 2. 进入iot-device-endpoint-package文件夹下，新建lib目录
cd iot-device-endpoint-package
if not exist lib (
  mkdir lib
) else (
  echo lib already exists, skipping mkdir command
)

rem 3. 将D:\work-for-iot\iot-device-endpoint\iot-device-web\target\lib\下的所有.jar文件
rem    复制到iot-device-endpoint-package\lib\下
xcopy ..\iot-device-web\target\lib\*.jar lib\
xcopy ..\iot-device-web\target\*.jar lib\

rem 4. 将以下三个文件也复制到iot-device-endpoint-package下
copy ..\02_build_image.sh .
copy ..\03_start.sh .
copy ..\Dockerfile .

rem 5. 将iot-device-endpoint-package文件夹压缩成iot-device-endpoint-package_%timestamp%.zip
powershell Compress-Archive -Path .\* -DestinationPath ..\iot-device-endpoint-package.zip

rem 6. 删掉整个iot-device-endpoint-package文件夹
cd ..
rmdir /s /q iot-device-endpoint-package

rem 7. 记录输出日志和异常报错到win_package_log.txt文件
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

rem exit /b %errorlevel%
