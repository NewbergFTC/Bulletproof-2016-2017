package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;
import static us.newberg.bulletproof.math.MathUtil.FeetToInches;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "AutoOneTest", group = "Test")
public class AutoOneTestOp extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        telemetry.addLine("Full steam ahead!");
        telemetry.update();

        _driveTrain.DriveStraight(0.5f, FeetToInches(6));   // 1/2 power for 6 feet
        idle();
        _driveTrain.Rotate(0.2f, 45);
        idle();
        _driveTrain.DriveStraight(0.25f, 10);
        idle();
        _driveTrain.Rotate(0.1f, 20);
        idle();
        _driveTrain.Rotate(0.1f, -40);
        idle();
        _driveTrain.Rotate(0.1f, 20);
    }
}
