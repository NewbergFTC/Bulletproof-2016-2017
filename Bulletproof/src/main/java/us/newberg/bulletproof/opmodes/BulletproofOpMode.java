package us.newberg.bulletproof.opmodes;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtUltrasonicSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import us.newberg.bulletproof.modules.DriveTrain;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.modules.ButtonPusher;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.Collector;

public abstract class BulletproofOpMode extends LinearOpMode
{
    protected DriveTrain _driveTrain;
    protected Flipper _flipper;
    protected ButtonPusher _buttonPusher;
    protected Collector _collector;

    protected HiTechnicNxtUltrasonicSensor _sonar;

    protected VuforiaLocalizer             _vuforia;
    protected VuforiaTrackables            _beacons;

    protected VuforiaTrackableDefaultListener _wheelsListener;
    protected VuforiaTrackableDefaultListener _toolsListener;
    protected VuforiaTrackableDefaultListener _legoListener;
    protected VuforiaTrackableDefaultListener _gearsListener;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
        _flipper = null;
        _buttonPusher = null;
        _collector = null;
    }

    protected void Init()
    {
        Motors.Init(hardwareMap);
        Servos.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
        _flipper = new Flipper(Motors.Flipper, telemetry);
        _buttonPusher = new ButtonPusher(Servos.BeaconLeft, Servos.BeaconRight, telemetry);
        _collector = new Collector(Motors.Collector);

        _sonar = (HiTechnicNxtUltrasonicSensor) hardwareMap.ultrasonicSensor.get("Sonar");

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        parameters.vuforiaLicenseKey = "AYblTAL/////AAAAGc2wFs8chEe1hqa/sjeckOVI8qu/kWhE0ESBxoph/FRyCgFyL2eg1hARujGog3FFfcV8eUyYvmkyOs/7XIOPidYGVA1ytKIoL/43imlszxrbtZQZVAZYIEm+KRpHDQB72ZoveW3DLq2NWQrBrdn+IFuvW/0EURd5JiV8530Qad/FQ9byPeMRSiG/xKK46vCShxBBrLzS4BZc+cqlCXIN+t1+HDUiav/srIebZLC7yJOVTTXl2EDxmtR4pYmhakxl4+e/aaVrf55+s0ZV8jy+cJGxLi9TvQsIc3iNzTbB3R7L9s/9bJ1XklfemXgSeAaOP+RDUI2uEQQiqLmjIUjYK9AgBSa0jA/UCJII+hVZ8nQX";

        _vuforia = ClassFactory.createVuforiaLocalizer(parameters);
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
    }

    protected void CleanUp()
    {
		// TODO(Garrison): Do we really need this?
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        _beacons.activate();

        Run();

        CleanUp();
    }

    public void Update() throws InterruptedException
    {
        telemetry.update();
        idle();
    }

    // TODO(Garrison): Test this
    public void MonitoredSleep(long millis)
    {
        if (millis < 100)
        {
            sleep(100);
            return;
        }

        final double SECONDS_TO_SLEEP = millis / 1000.0;
        double time = getRuntime();
        final double TARGET_TIME = time + SECONDS_TO_SLEEP;

        final long SLEEP_INTERVAL = (millis % 2) == 0 ? 4 : 5;

        while (time < TARGET_TIME)
        {
            sleep(SLEEP_INTERVAL);
            idle();

            time = getRuntime();
        }
    }

    abstract protected void Run() throws InterruptedException;
}
