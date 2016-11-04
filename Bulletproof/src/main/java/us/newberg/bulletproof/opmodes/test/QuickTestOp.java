package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "QuickTest", group = "Test")
public class QuickTestOp extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        while (opModeIsActive())
        {
            int ticks = Motors.FrontLeft().GetCurrentTicks();

            Motors.FrontLeft().SetPower(0.06);

            telemetry.addData("Ticks", ticks);
            telemetry.update();

            idle();
        }
    }
}
