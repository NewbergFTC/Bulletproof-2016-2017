package us.newberg.bulletproof.modules;

import com.elvishew.xlog.XLog;
import com.qualcomm.robotcore.hardware.DcMotor;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;

import us.or.k12.newberg.newbergcommon.WatchDog;

public class Collector
{
    public static final String TAG = "Collector";

    public static double MAIN_POWER = 1.0;
    public static double STOP_POWER = 0.0;

    private DcMotor _motor;

    private WatchDog _watchDog;

    public Collector(DcMotor motor)
    {
        _motor = motor;
        Stop();

        XLog.tag(TAG).d("Initialized");
    }

    public void StartPull()
    {
        _motor.setPower(MAIN_POWER);

        XLog.tag(TAG).d("Pulling");
    }

    public void StartPush()
    {
        _motor.setPower(-MAIN_POWER);

        XLog.tag(TAG).d("Pushing");
    }

    public void Stop()
    {
        _motor.setPower(STOP_POWER);

        XLog.tag(TAG).d("Stop");
    }
}
