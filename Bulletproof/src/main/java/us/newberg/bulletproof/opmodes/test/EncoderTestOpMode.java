package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;
import static us.newberg.bulletproof.math.MathUtil.FeetToInches;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Encoder Test", group = "Test")
@Disabled
public class EncoderTestOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        telemetry.addLine("Full steam ahead!");
        telemetry.update();

        _driveTrain.DriveStraight(0.5f, FeetToInches(6), telemetry, this);   // 1/2 power for 6 feet
        idle();
        _driveTrain.DriveStraight(0.25f, FeetToInches(-3), telemetry, this); // 1/2 power for 3 feet backwards
        idle();
        _driveTrain.DriveStraight(0.1f, FeetToInches(-3), telemetry, this);  // 1/10 power for 3 feet
        idle();
        _driveTrain.DriveStraight(0.1f, FeetToInches(5), telemetry, this);   // 1/10 power for 5 feet
        idle();
        _driveTrain.DriveStraight(1.0f, FeetToInches(-5), telemetry, this);  // 1/1 power for 5 feet back wards
        idle();

        _driveTrain.Rotate(0.5f, 180, this);    // 1/2 power, 180 degrees
        idle();
        _driveTrain.Rotate(0.5f, -180, this);   // 1/2 power, -180 degrees
        idle();
        _driveTrain.Rotate(0.1f, 360, this);    // 1/10 power, -360 degrees
        idle();
        _driveTrain.Rotate(0.1f, -90, this);    // 1/10 power, -90 degrees
        idle();
        _driveTrain.Rotate(0.1f, 45, this);     // 1/10 power, 45 degrees
        idle();
        _driveTrain.Rotate(0.25f, -45, this);   // 1/4 power, -45 degrees
        idle();
        _driveTrain.Rotate(0.25f, -270, this);  // 1/4 power, -180 degrees
        idle();
    }
}
