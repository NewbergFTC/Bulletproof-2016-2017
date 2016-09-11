package us.newberg.bulletproof;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class BulletproofOpMode extends LinearOpMode
{
    DcMotor _testMotor;

    public BulletproofOpMode()
    {
        super();

        // NOTE(Garrison): Don't init any ftc objects here.
    }

    protected void Init()
    {
        _testMotor = hardwareMap.dcMotor.get("testMotor");
    }
}
