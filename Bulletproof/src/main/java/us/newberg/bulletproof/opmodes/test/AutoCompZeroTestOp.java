package us.newberg.bulletproof.opmodes.test;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
public class AutoCompZeroTestOp extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        while (opModeIsActive())
        {
            _driveTrain.UpdateTelemetry();
            telemetry.update();
            idle();
        }

        CleanUp();
    }
}
