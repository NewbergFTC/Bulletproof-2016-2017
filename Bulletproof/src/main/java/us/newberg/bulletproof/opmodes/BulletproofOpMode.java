package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import us.newberg.bulletproof.DriveTrain;
import us.newberg.bulletproof.lib.Motors;

public abstract class BulletproofOpMode extends LinearOpMode
{
    protected  DriveTrain _driveTrain;

    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
    }

    protected void Init()
    {
        Motors.Init(hardwareMap);

        _driveTrain = new DriveTrain(telemetry);
    }

    protected void CleanUp()
    {

    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        Run();

        CleanUp();
    }

    protected void Update() throws InterruptedException
    {
        telemetry.update();
        idle();
    }

    abstract protected void Run() throws InterruptedException;
}
