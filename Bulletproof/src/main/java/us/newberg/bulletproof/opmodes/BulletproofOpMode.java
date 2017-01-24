package us.newberg.bulletproof.opmodes;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtUltrasonicSensor;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;

import us.newberg.bulletproof.modules.DriveTrain;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.modules.ButtonPusher;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.Collector;
import us.newberg.bulletproof.modules.Beacon;

public abstract class BulletproofOpMode extends LinearVisionOpMode
{
    protected DriveTrain _driveTrain;
    protected Flipper _flipper;
    protected ButtonPusher _buttonPusher;
    protected Collector _collector;
    protected Beacon _beacon;

    protected HiTechnicNxtUltrasonicSensor _sonar;
    protected HiTechnicNxtLightSensor      _lineFollower;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
        _flipper = null;
        _buttonPusher = null;
        _collector = null;
        _beacon = null;
    }

    protected void Init()
    {
        Motors.Init(hardwareMap);
        Servos.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
        _flipper = new Flipper(Motors.Flipper, telemetry);
        _buttonPusher = new ButtonPusher(Servos.BeaconLeft, Servos.BeaconRight, telemetry);
        _collector = new Collector(Motors.Collector);
        _beacon = new Beacon(telemetry);

        _sonar = (HiTechnicNxtUltrasonicSensor) hardwareMap.ultrasonicSensor.get("Sonar");
        _lineFollower = (HiTechnicNxtLightSensor) hardwareMap.lightSensor.get("light");
    }

    protected void CleanUp()
    {
		// TODO(Garrison): Do we really need this?
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

//        beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);

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
        if (hasNewFrame())
        {
            _beacon.Update(beacon.getAnalysis());
        }

        telemetry.update();
        waitOneFullHardwareCycle();
    }

    public void Idle() throws InterruptedException
    {
        waitOneFullHardwareCycle();
    }

    abstract protected void Run() throws InterruptedException;
}
