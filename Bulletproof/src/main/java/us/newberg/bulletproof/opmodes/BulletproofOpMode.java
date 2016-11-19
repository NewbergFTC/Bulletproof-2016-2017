package us.newberg.bulletproof.opmodes;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

import us.newberg.bulletproof.DriveTrain;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.modules.ButtonPusher;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.HopperDoor;

public abstract class BulletproofOpMode extends LinearVisionOpMode
{
    protected DriveTrain _driveTrain;
    protected HopperDoor _hopperDoor;
    protected Flipper _flipper;
    protected ButtonPusher _buttonPusher;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
        _hopperDoor = null;
        _flipper = null;
        _buttonPusher = null;
    }

    protected void Init()
    {
        Motors.Init(hardwareMap);
        Servos.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
        _hopperDoor = new HopperDoor(Servos.HopperDoor, telemetry);
        _flipper = new Flipper(Motors.Flipper, telemetry);
        _buttonPusher = new ButtonPusher(Servos.BeaconLeft, Servos.BeaconRight, telemetry);
    }

    protected void CleanUp()
    {

    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForVisionStart();

        setCamera(Cameras.SECONDARY);
        setFrameSize(new Size(900, 900));

        enableExtension(Extensions.BEACON);
        enableExtension(Extensions.ROTATION);
        enableExtension(Extensions.CAMERA_CONTROL);

        beacon.setAnalysisMethod(Beacon.AnalysisMethod.COMPLEX);

        beacon.setColorToleranceRed(0);
        beacon.setColorToleranceBlue(-0.1);

        rotation.setIsUsingSecondaryCamera(true);
        rotation.disableAutoRotate();
        rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);

        cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        cameraControl.setAutoExposureCompensation();

        Init();

        waitForStart();

        Run();

        CleanUp();
    }

    public void Update() throws InterruptedException
    {
//        telemetry.addData("Beacon Color", beacon.getAnalysis().getColorString());
//        telemetry.addData("Beacon Center", beacon.getAnalysis().getLocationString());
//        telemetry.addData("Beacon Confidence", beacon.getAnalysis().getConfidenceString());
//        telemetry.addData("Beacon Buttons", beacon.getAnalysis().getButtonString());
//        telemetry.addData("Screen Rotation", rotation.getScreenOrientationActual());
//        telemetry.addData("Frame Rate", fps.getFPSString() + " FPS");
//        telemetry.addData("Frame Size", "Width: " + width + " Height: " + height);

        telemetry.update();
        waitOneFullHardwareCycle();
    }

    public void Idle() throws InterruptedException
    {
        waitOneFullHardwareCycle();
    }

    abstract protected void Run() throws InterruptedException;
}
