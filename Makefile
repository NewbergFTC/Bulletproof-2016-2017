.PHONY: build install start

all: build install start

build:
	@printf "\e[34mBuilding...\n"
	@./gradlew :Bulletproof:build

install:
	@printf "\e[34mInstalling...\n"
	@adb push ./Bulletproof/build/outputs/apk/Bulletproof-debug.apk /data/local/tmp/com.qualcomm.ftcrobotcontroller
	@adb shell pm install -r "/data/local/tmp/com.qualcomm.ftcrobotcontroller"

start:
	@printf "\e[34mStarting...\n"
	@adb shell am start -n "com.qualcomm.ftcrobotcontroller/org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
