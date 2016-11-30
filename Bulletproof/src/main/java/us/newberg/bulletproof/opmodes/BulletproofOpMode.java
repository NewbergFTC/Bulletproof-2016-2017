package us.newberg.bulletproof.opmodes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.CompassSensor;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

import us.newberg.bulletproof.DriveTrain;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.math.Quaternion;
import us.newberg.bulletproof.modules.ButtonPusher;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.HopperDoor;

public abstract class BulletproofOpMode extends LinearVisionOpMode implements SensorEventListener
{
    protected DriveTrain _driveTrain;
    protected HopperDoor _hopperDoor;
    protected Flipper _flipper;
    protected ButtonPusher _buttonPusher;

    protected SensorManager _sensorManager;
    protected Sensor _rotationSensor;

    protected Quaternion _rotation;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
        _hopperDoor = null;
        _flipper = null;
        _buttonPusher = null;

        _sensorManager = null;
        _rotationSensor = null;

        _rotation = null;
    }

    protected void Init()
    {
        Motors.Init(hardwareMap);
        Servos.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
        _hopperDoor = new HopperDoor(Servos.HopperDoor, telemetry);
        _flipper = new Flipper(Motors.Flipper, telemetry);
        _buttonPusher = new ButtonPusher(Servos.BeaconLeft, Servos.BeaconRight, telemetry);


        _sensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        _rotationSensor = _sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        _sensorManager.registerListener(this, _rotationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            _rotation = new Quaternion(event.values[0], event.values[1], event.values[2], event.values[3]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

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

        beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);

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

        telemetry.addData("Rotation x: ", _rotation.x);
        telemetry.addData("Rotation y: ", _rotation.y);
        telemetry.addData("Rotation z: ", _rotation.z);
        telemetry.addData("Rotation w: ", _rotation.w);

        telemetry.update();
        waitOneFullHardwareCycle();
    }

    public void Idle() throws InterruptedException
    {
        waitOneFullHardwareCycle();
    }

    abstract protected void Run() throws InterruptedException;
}
