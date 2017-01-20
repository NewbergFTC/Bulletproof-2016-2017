.PHONY: build upload start

all: upload start

build:
	./gradlew :Bulletproof:build

upload:
	./gradlew :Bulletproof:InstallDebug

start:
	adb shell am start -n com.qualcomm.ftcrobotcontroller/org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity
