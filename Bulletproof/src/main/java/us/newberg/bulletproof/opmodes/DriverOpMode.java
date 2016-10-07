package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.math.Vector2f;

/**
 * FTC team 6712 Bulletproof
 */

@TeleOp(name = "DriverOpMode", group = "Driver")
public class DriverOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        Vector2f leftDrivePower = new Vector2f();
        Vector2f rightDrivePower = new Vector2f();

        waitForStart();

        while(opModeIsActive())
        {
            float gamepadOneLeftY          = gamepad1.left_stick_y;     // Left side wheels
            float gamepadOneRightY         = gamepad1.right_stick_y;    // Right side wheels
            boolean gamepadOneLeftBumper   = gamepad1.left_bumper;      // Move Left - without rotation
            boolean gamepadeOneRightBumper = gamepad1.right_bumper;     // Move Right - without rotation

            final float LEFT_RIGHT_POWER = 0.75f;

            if (gamepadOneLeftBumper)
            {
                leftDrivePower.x = LEFT_RIGHT_POWER;
                leftDrivePower.y = -LEFT_RIGHT_POWER;

                rightDrivePower.x = LEFT_RIGHT_POWER;
                rightDrivePower.y = -LEFT_RIGHT_POWER;
            }
            else if (gamepadeOneRightBumper)
            {
                leftDrivePower.x = -LEFT_RIGHT_POWER;
                leftDrivePower.y = LEFT_RIGHT_POWER;

                rightDrivePower.x = -LEFT_RIGHT_POWER;
                rightDrivePower.y = LEFT_RIGHT_POWER;
            }
            else
            {
                leftDrivePower.x = gamepadOneLeftY;
                leftDrivePower.y = gamepadOneLeftY;

                rightDrivePower.x = -gamepadOneRightY;
                rightDrivePower.y = -gamepadOneRightY;
            }

            _driveTrain.Drive(leftDrivePower, rightDrivePower);

            _driveTrain.UpdateTelemetry();
            telemetry.update();
            idle();
        }
    }
}
