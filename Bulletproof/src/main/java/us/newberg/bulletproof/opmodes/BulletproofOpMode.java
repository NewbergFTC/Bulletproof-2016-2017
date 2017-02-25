package us.newberg.bulletproof.opmodes;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import com.elvishew.xlog.XLog;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import us.newberg.bulletproof.lib.Sensors;
import us.newberg.bulletproof.modules.DriveTrain;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.modules.ButtonPusher;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.Collector;
import us.newberg.bulletproof.modules.Hopper;
import us.newberg.bulletproof.modules.LightSensor;
import us.or.k12.newberg.newbergcommon.BeaconUtil;
import us.or.k12.newberg.newbergcommon.math.Vector2f;
import us.or.k12.newberg.newbergcommon.vuforia.NewbergVuforiaLocal;
import us.or.k12.newberg.newbergcommon.math.MathUtil;
import us.or.k12.newberg.newbergcommon.vuforia.VuforiaUtil;

import com.qualcomm.ftcrobotcontroller.R;

public abstract class BulletproofOpMode extends LinearOpMode
{
    static
    {
        System.loadLibrary("opencv_java3");
    }

    protected DriveTrain _driveTrain;
    protected Flipper _flipper;
    protected ButtonPusher _buttonPusher;
    protected Collector _collector;
    protected Hopper _hopper;
    protected LightSensor _lightSensor;

    protected NewbergVuforiaLocal _vuforia;
    protected VuforiaTrackables   _beacons;

    protected VuforiaTrackableDefaultListener _wheelsListener;
    protected VuforiaTrackableDefaultListener _toolsListener;
    protected VuforiaTrackableDefaultListener _legoListener;
    protected VuforiaTrackableDefaultListener _gearsListener;

    protected VuforiaTrackableDefaultListener[] _listeners;

    protected boolean _enableLogging = true;

    public static final float MM_BOT_WIDTH = 17.0f * MathUtil.MM_PER_INCH;
    
    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
        _flipper = null;
        _buttonPusher = null;
        _collector = null;
        _hopper = null;
        _lightSensor = null;
    }

    protected void InitVuforia()
    {
        XLog.d("Vuforia Calibrate Start");

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        parameters.useExtendedTracking = false;
        parameters.vuforiaLicenseKey = "AYblTAL/////AAAAGc2wFs8chEe1hqa/sjeckOVI8qu/kWhE0ESBxoph/FRyCgFyL2eg1hARujGog3FFfcV8eUyYvmkyOs/7XIOPidYGVA1ytKIoL/43imlszxrbtZQZVAZYIEm+KRpHDQB72ZoveW3DLq2NWQrBrdn+IFuvW/0EURd5JiV8530Qad/FQ9byPeMRSiG/xKK46vCShxBBrLzS4BZc+cqlCXIN+t1+HDUiav/srIebZLC7yJOVTTXl2EDxmtR4pYmhakxl4+e/aaVrf55+s0ZV8jy+cJGxLi9TvQsIc3iNzTbB3R7L9s/9bJ1XklfemXgSeAaOP+RDUI2uEQQiqLmjIUjYK9AgBSa0jA/UCJII+hVZ8nQX";
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.NONE;

        _vuforia = new NewbergVuforiaLocal(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        _beacons = _vuforia.loadTrackablesFromAsset("FTC_2016-17");
        _beacons.get(0).setName("Wheels");
        _beacons.get(1).setName("Tools");
        _beacons.get(2).setName("Lego");
        _beacons.get(3).setName("Gears");

        _wheelsListener = (VuforiaTrackableDefaultListener) _beacons.get(0).getListener();
        _toolsListener  = (VuforiaTrackableDefaultListener) _beacons.get(1).getListener();
        _legoListener   = (VuforiaTrackableDefaultListener) _beacons.get(2).getListener();
        _gearsListener  = (VuforiaTrackableDefaultListener) _beacons.get(3).getListener();

        _listeners = new VuforiaTrackableDefaultListener[4];
        _listeners[0] = _wheelsListener;
        _listeners[1] = _toolsListener;
        _listeners[2] = _legoListener;
        _listeners[3] = _gearsListener;

        if (_enableLogging)
        {
            XLog.tag("BulletproofOpMode").d("Vuforia Calibrate Complete");
        }
    }

    protected void Init()
    {
        if (_enableLogging)
        {
            XLog.tag("BulletproofOpMode").d("Calibrate Start");
        }

        Motors.Init(hardwareMap);
        Servos.Init(hardwareMap);
        Sensors.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
        _flipper = new Flipper(Motors.Flipper);
        _buttonPusher = new ButtonPusher(Servos.BeaconLeft, Servos.BeaconRight);
        _collector = new Collector(Motors.Collector);
        _hopper = new Hopper(Servos.HopperDoor);
        _lightSensor = new LightSensor(Sensors.LightSensor, Servos.SensorArm);

        InitVuforia();

        _lightSensor.Calibrate();

        if (_enableLogging)
        {
            XLog.tag("BulletproofOpMode").d("Calibration Complete");
        }
    }

    protected void CleanUp()
    {
		// TODO(Garrison): Do we really need this?
        // Maybe flush some logs or something
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        if (_enableLogging)
        {
            XLog.tag("BulletproofOpMode").d("Waiting for start...");
        }

        waitForStart();

        _beacons.activate();

        Run();

        CleanUp();
    }

    protected BeaconUtil.BeaconStatus AnalyzeBeacon(VuforiaTrackableDefaultListener listener)
    {
        return BeaconUtil.GetBeaconStatus(_vuforia.GetImage(), listener,
                _vuforia.getCameraCalibration());
    }

    protected BeaconUtil.BeaconStatus AnalyzeAndDeployBlue(VuforiaTrackableDefaultListener listener)
    {
        BeaconUtil.BeaconStatus beaconStatus = AnalyzeBeacon(listener);

        switch (beaconStatus)
        {
            case BEACON_BLUE_RED:
                _buttonPusher.DeployLeft();

                break;

            case BEACON_RED_BLUE:
                _buttonPusher.DeployRight();

                break;

            case BEACON_ALL_BLUE:
                _buttonPusher.CloseRight();
                _buttonPusher.CloseLeft();

                break;

            case BEACON_NO_BLUE:
                _buttonPusher.DeployLeft();
                _buttonPusher.DeployRight();

                break;

            default:
                break;
        }

        return beaconStatus;
    }

    protected BeaconUtil.BeaconStatus AnalyzeAndDeployRed(VuforiaTrackableDefaultListener listener)
    {
        BeaconUtil.BeaconStatus beaconStatus = AnalyzeBeacon(listener);

        switch (beaconStatus)
        {
            case BEACON_BLUE_RED:
                _buttonPusher.DeployRight();

                break;

            case BEACON_RED_BLUE:
                _buttonPusher.DeployLeft();

                break;

            case BEACON_ALL_BLUE:
                _buttonPusher.DeployRight();
                _buttonPusher.DeployLeft();

                break;

            case BEACON_NO_BLUE:
                _buttonPusher.CloseLeft();
                _buttonPusher.CloseRight();

                break;

            default:
                break;
        }

        return beaconStatus;
    }

    protected void StraightenToBeacon(VuforiaTrackableDefaultListener listener)
    {
        VectorF angles = VuforiaUtil.AnglesFromTarget(listener);
        VectorF translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

        Vector2f leftPower = new Vector2f();
        Vector2f rightPower = new Vector2f();

        final float horizontalPower = 0.08f;
        final float forwardPower = 0.13f;

        float[] poseData = poseData = listener.getRawPose().getData();

        while (opModeIsActive())
        {
            if (listener.getPose() != null)
            {
                try
                {
                    translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                            Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

                    angles = VuforiaUtil.AnglesFromTarget(listener);

                    poseData = listener.getRawPose().getData();
                }
                catch (Exception e)
                {
                    telemetry.addData("HOLY FUCK", "WE'RE DEAD");
                }
            }

            if (listener.isVisible())
            {
                if (translation.get(0) < -550)
                {
                    leftPower.x = horizontalPower;
                    leftPower.y = horizontalPower;

                    rightPower.x = horizontalPower;
                    rightPower.y = horizontalPower;
                }
                else if (translation.get(0) > -450)
                {
                    leftPower.x = -horizontalPower;
                    leftPower.y = -horizontalPower;

                    rightPower.x = -horizontalPower;
                    rightPower.y = -horizontalPower;
                }
                else if (poseData[1] < 0.93f)
                {
                    if (poseData[2] > 0)
                    {
                        // Right
                        leftPower.x = horizontalPower;
                        leftPower.y = horizontalPower;

                        rightPower.x = -horizontalPower;
                        rightPower.y = -horizontalPower;
                    }
                    else
                    {
                        // Left
                        leftPower.x = -horizontalPower;
                        leftPower.y = -horizontalPower;

                        rightPower.x = horizontalPower;
                        rightPower.y = horizontalPower;
                    }
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }

            _driveTrain.Drive(leftPower, rightPower);

            idle();
        }

        _driveTrain.StopAll();
    }

    protected void GetToBeacon(VuforiaTrackableDefaultListener listener)
    {
        VectorF angles = VuforiaUtil.AnglesFromTarget(listener);
        VectorF translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

        Vector2f leftPower = new Vector2f();
        Vector2f rightPower = new Vector2f();

        final float horizontalPower = 0.08f;
        final float forwardPower = 0.13f;

        float[] poseData = poseData = listener.getRawPose().getData();

        while (opModeIsActive())
        {
            if (listener.getPose() != null)
            {
                try
                {
                    translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                            Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

                    angles = VuforiaUtil.AnglesFromTarget(listener);

                    poseData = listener.getRawPose().getData();
                }
                catch (Exception e)
                {
                    telemetry.addData("HOLY FUCK", "WE'RE DEAD");
                }
            }

            if (listener.isVisible())
            {
                if (translation.get(0) < -540)
                {
                    leftPower.x = horizontalPower;
                    leftPower.y = horizontalPower;

                    rightPower.x = horizontalPower;
                    rightPower.y = horizontalPower;
                }
                else if (translation.get(0) > -460)
                {
                    leftPower.x = -horizontalPower;
                    leftPower.y = -horizontalPower;

                    rightPower.x = -horizontalPower;
                    rightPower.y = -horizontalPower;
                }
                else if (poseData[1] < 0.935f)
                {
                    if (poseData[2] > 0)
                    {
                        // Right
                        leftPower.x = horizontalPower;
                        leftPower.y = horizontalPower;

                        rightPower.x = -horizontalPower;
                        rightPower.y = -horizontalPower;
                    }
                    else
                    {
                        // Left
                        leftPower.x = -horizontalPower;
                        leftPower.y = -horizontalPower;

                        rightPower.x = horizontalPower;
                        rightPower.y = horizontalPower;
                    }
                }
                else
                {
                    leftPower.x = -forwardPower;
                    leftPower.y = forwardPower;

                    rightPower.x = -forwardPower;
                    rightPower.y = forwardPower;
                }
            }
            else
            {
                break;
            }

            _driveTrain.Drive(leftPower, rightPower);

            idle();
        }

        _driveTrain.StopAll();
    }


    public void Update() throws InterruptedException
    {
        telemetry.update();
        idle();
    }

    abstract protected void Run() throws InterruptedException;
}
