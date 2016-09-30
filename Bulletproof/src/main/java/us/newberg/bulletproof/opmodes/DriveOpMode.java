package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.math.Vector2f;

/**
 * FTC team 6712 Bulletproof
 */

@TeleOp(name = "DriverOpMode", group = "Driver")
public class DriveOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        while(opModeIsActive())
        {
            float gamepadOneLeftY          = gamepad1.left_stick_y;     // Left side wheels
            float gamepadOneRightY         = gamepad1.right_stick_y;    // Right side wheels
            boolean gamepadOneLeftBumper   = gamepad1.left_bumper;      // Move Left - without rotation
            boolean gamepadeOneRightBumper = gamepad1.right_bumper;     // Move Right - without rotation

            Vector2f leftDrivePower;
            Vector2f rightDrivePower;

            final float LEFT_RIGHT_POWER = 0.75f;

            if (gamepadOneLeftBumper)
            {
                leftDrivePower = new Vector2f(LEFT_RIGHT_POWER, -LEFT_RIGHT_POWER);
                rightDrivePower = new Vector2f(LEFT_RIGHT_POWER, -LEFT_RIGHT_POWER);
            }
            else if (gamepadeOneRightBumper)
            {
                leftDrivePower = new Vector2f(-LEFT_RIGHT_POWER, LEFT_RIGHT_POWER);
                rightDrivePower = new Vector2f(-LEFT_RIGHT_POWER, LEFT_RIGHT_POWER);
            }
            else
            {
                leftDrivePower = new Vector2f(gamepadOneLeftY);
                rightDrivePower = new Vector2f(-gamepadOneRightY);
            }

            _driveTrain.Drive(leftDrivePower, rightDrivePower);

            telemetry.update();
            idle();
        }
    }
}
