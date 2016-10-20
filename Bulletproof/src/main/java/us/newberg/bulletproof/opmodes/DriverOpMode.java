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
            final float LEFT_RIGHT_POWER = 0.75f;

            Vector2f gamepadOneLeft = new Vector2f(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            Vector2f gamepadOneRight = new Vector2f(gamepad1.right_stick_x, -gamepad1.right_stick_y);

            float gamepadOneLeftAngle = (float) gamepadOneLeft.Normalized().AngleWithXAxis();
            int gamepadOneLeftAngleQuad;

            float gamepadOneRightAngle = (float) gamepadOneRight.Normalized().AngleWithXAxis();
            int gamepadOneRightAngleQuad;

            if (gamepadOneLeft.x > 0)
            {
                if (gamepadOneLeftAngle > 0)
                {
                    gamepadOneLeftAngleQuad = 1;
                }
                else
                {
                    gamepadOneLeftAngleQuad = 4;
                }
            }
            else
            {
                if (gamepadOneLeftAngle > 0)
                {
                    gamepadOneLeftAngleQuad = 3;
                }
                else
                {
                    gamepadOneLeftAngleQuad = 2;
                }
            }

            if (gamepadOneRight.x > 0)
            {
                if (gamepadOneRightAngle > 0)
                {
                    gamepadOneRightAngleQuad = 1;
                }
                else
                {
                    gamepadOneRightAngleQuad = 4;
                }
            }
            else
            {
                if (gamepadOneRightAngle > 0)
                {
                    gamepadOneRightAngleQuad = 3;
                }
                else
                {
                    gamepadOneRightAngleQuad = 2;
                }
            }

            float powerY = gamepadOneLeft.y;
            float powerX = gamepadOneLeft.x;

            switch (gamepadOneLeftAngleQuad)
            {
                case 1:
                    if (gamepadOneLeftAngle >= 45)
                    {
                        // Forward
                        leftDrivePower.y = leftDrivePower.x = powerY;
                        rightDrivePower.y = rightDrivePower.x = powerY;
                    }
                    else
                    {
                        // Right
                        leftDrivePower.x = powerX;
                        leftDrivePower.y = -powerX;

                        rightDrivePower.x = -powerX;
                        rightDrivePower.y = powerX;
                    }
                    break;
                case 2:
                    if (gamepadOneLeftAngle >= 45)
                    {
                        // Forward
                        leftDrivePower.y = leftDrivePower.x = powerY;
                        rightDrivePower.y = rightDrivePower.x = powerY;
                    }
                    else
                    {
                        // Left
                        leftDrivePower.x = -powerX;
                        leftDrivePower.y = powerX;

                        rightDrivePower.x = powerX;
                        rightDrivePower.y = -powerX;
                    }
                    break;
                case 3:
                    if (gamepadOneLeftAngle > -45)
                    {
                        // Left
                        leftDrivePower.x = -powerX;
                        leftDrivePower.y = powerX;

                        rightDrivePower.x = powerX;
                        rightDrivePower.y = -powerX;
                    }
                    else
                    {
                        // Back
                        leftDrivePower.y = leftDrivePower.x = -powerY;
                        rightDrivePower.y = rightDrivePower.x = -powerY;
                    }
                    break;
                case 4:
                    if (gamepadOneLeftAngle > -45)
                    {
                        // Right
                        leftDrivePower.x = powerX;
                        leftDrivePower.y = -powerX;

                        rightDrivePower.x = -powerX;
                        rightDrivePower.y = powerX;
                    }
                    else
                    {
                        // Back
                        leftDrivePower.y = leftDrivePower.x = -powerY;
                        rightDrivePower.y = rightDrivePower.x = -powerY;
                    }
                    break;
            }

            _driveTrain.Drive(leftDrivePower, rightDrivePower);


            telemetry.addData("Left angle and quad", "%f, %d", gamepadOneLeftAngle, gamepadOneLeftAngleQuad);
            telemetry.addData("Right angle and quad", "%f, %d", gamepadOneRightAngle, gamepadOneRightAngleQuad);

            _driveTrain.UpdateTelemetry();
            telemetry.update();
            idle();
        }
    }
}
