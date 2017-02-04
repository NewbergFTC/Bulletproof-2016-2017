package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;

import us.or.k12.newberg.newbergcommon.WatchDog;

public class Collector
{
    public static double MAIN_POWER = 1.0;
    public static double STOP_POWER = 0.0;

    private DcMotor _motor;

    private WatchDog _watchDog;

    public Collector(DcMotor motor)
    {
        _motor = motor;
        Stop();
    }

    public void PullForCountBlocking(BulletproofOpMode caller, long millis) throws InterruptedException
    {
        StartPull();
        caller.sleep(millis);
        Stop();
    }

    public void PullForCount(BulletproofOpMode caller, final long millis) throws InterruptedException
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                StartPull();
                try
                {
                    Thread.sleep(millis);
                } catch (InterruptedException e)
                {
                    // TODO(Garrison): Error handling
                    e.printStackTrace();
                }
                Stop();
            }
        }).start();
    }

    public void StartPull()
    {
        _motor.setPower(MAIN_POWER);
    }

    public void StartPush()
    {
        _motor.setPower(-MAIN_POWER);
    }

    public void Stop()
    {
        _motor.setPower(STOP_POWER);
    }

    public void SetPower(double power)
    {
        _motor.setPower(power);
    }

    public void UpdateMotor(DcMotor motor)
    {
        _motor = motor;
    }
}
