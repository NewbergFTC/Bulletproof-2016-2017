package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import us.newberg.bulletproof.DriveTrain;

public abstract class BulletproofOpMode extends LinearOpMode
{
    DriveTrain _driveTrain;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
    }

    protected void Init()
    {
        telemetry.setAutoClear(false);
        _driveTrain = new DriveTrain(hardwareMap, telemetry);
    }
}
