package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.Motor;
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

        boolean collectorToggle = false;

        waitForStart();

        double startTime = getRuntime();
        double collectorCooldown = 0;

        while(opModeIsActive())
        {
            final double currentTime = getRuntime();

            final float LEFT_RIGHT_POWER = 0.75f;

            Vector2f gamepadOneLeft = new Vector2f(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            Vector2f gamepadOneRight = new Vector2f(gamepad1.right_stick_x, gamepad1.right_stick_y);

            float gamepadOneLeftAngle = (float) Math.abs(gamepadOneLeft.Normalized().AngleWithXAxis());
            int gamepadOneLeftAngleQuad;

            if (gamepadOneLeft.x >= 0 && gamepadOneLeft.y >=0)
            {
                // One
                gamepadOneLeftAngleQuad = 1;
            }
            else if (gamepadOneLeft.x < 0 && gamepadOneLeft.y >= 0)
            {
                // Two
                gamepadOneLeftAngleQuad = 2;
            }
            else if (gamepadOneLeft.x < 0 && gamepadOneLeft.y < 0)
            {
                // Three
                gamepadOneLeftAngleQuad = 3;
            }
            else if (gamepadOneLeft.x >= 0 && gamepadOneLeft.y < 0)
            {
                // Four
                gamepadOneLeftAngleQuad = 4;
            }
            else
            {
                // TODO(Garrison): Error Handling
                gamepadOneLeftAngleQuad = 1;
            }

            float powerY = Math.abs(gamepadOneLeft.y);
            float powerX = Math.abs(gamepadOneLeft.x);

            leftDrivePower.x = 0;
            leftDrivePower.y = 0;
            rightDrivePower.x = 0;
            rightDrivePower.y = 0;

            switch (gamepadOneLeftAngleQuad)
            {
                case 1:
                    if (gamepadOneLeftAngle >= 70)
                    {
                        // Forward
                        leftDrivePower.y = powerY;
                        leftDrivePower.x = powerY;

                        rightDrivePower.y = -powerY;
                        rightDrivePower.x = -powerY;
                    }
                    else if ((gamepadOneLeftAngle > 30) && gamepadOneLeftAngle < 70)
                    {
                        // Forward Right
                        leftDrivePower.x = powerY;
                        leftDrivePower.y = 0;

                        rightDrivePower.x = 0;
                        rightDrivePower.y = -powerY;
                    }
                    else if (gamepadOneLeftAngle <= 30)
                    {
                        // Right
                        leftDrivePower.x = powerX;
                        leftDrivePower.y = -powerX;

                        rightDrivePower.x = powerX;
                        rightDrivePower.y = -powerX;
                    }
                    break;
                case 2:
                    if (gamepadOneLeftAngle >= 70)
                    {
                        // Forward
                        leftDrivePower.y = powerY;
                        leftDrivePower.x = powerY;

                        rightDrivePower.y = -powerY;
                        rightDrivePower.x = -powerY;
                    }
                    else if (gamepadOneLeftAngle < 70 && gamepadOneLeftAngle > 30)
                    {
                        // Forward Left
                        leftDrivePower.x = 0;
                        leftDrivePower.y = powerY;

                        rightDrivePower.x = -powerY;
                        rightDrivePower.y = 0;
                    }
                    else if (gamepadOneLeftAngle <= 30)
                    {
                        // Left
                        leftDrivePower.x = -powerX;
                        leftDrivePower.y = powerX;

                        rightDrivePower.x = -powerX;
                        rightDrivePower.y = powerX;
                    }
                    break;
                case 3:
                    if (gamepadOneLeftAngle <= 30)  
                    {
                        // Left
                        leftDrivePower.x = -powerX;
                        leftDrivePower.y = powerX;

                        rightDrivePower.x = -powerX;
                        rightDrivePower.y = powerX;
                    }
                    else if (gamepadOneLeftAngle > 30 && gamepadOneLeftAngle < 70)
                    {
                        // Back Left
                        leftDrivePower.x = -powerY;
                        leftDrivePower.y = 0;

                        rightDrivePower.x = 0;
                        rightDrivePower.y = powerY;
                    }
                    else if (gamepadOneLeftAngle >= 70)
                    {
                        // Back
                        leftDrivePower.y = -powerY;
                        leftDrivePower.x = -powerY;

                        rightDrivePower.y = powerY;
                        rightDrivePower.x = powerY;
                    }
                    break;
                case 4:
                    if (gamepadOneLeftAngle <= 30)
                    {
                        // Right
                        leftDrivePower.x = powerX;
                        leftDrivePower.y = -powerX;

                        rightDrivePower.x = powerX;
                        rightDrivePower.y = -powerX;
                    }
                    else if (gamepadOneLeftAngle > 30 && gamepadOneLeftAngle < 70)
                    {
                        // Back Right
                        leftDrivePower.x = 0;
                        leftDrivePower.y = -powerY;

                        rightDrivePower.x = powerY;
                        rightDrivePower.y = 0;
                    }
                    else if (gamepadOneLeftAngle >= 70)
                    {
                        // Back
                        leftDrivePower.y = -powerY;
                        leftDrivePower.x = -powerY;

                        rightDrivePower.y = powerY;
                        rightDrivePower.x = powerY;
                    }
                    break;
            }

            powerX = gamepadOneRight.x;

            if (Math.abs(gamepadOneRight.x) >= 0.1f )
            {
                // Right
                leftDrivePower.x = powerX;
                leftDrivePower.y = powerX;

                rightDrivePower.x = powerX;
                rightDrivePower.y = powerX;
            }

            _driveTrain.Drive(leftDrivePower, rightDrivePower);
            telemetry.addData("Left angle and quad", "%f, %d", gamepadOneLeftAngle, gamepadOneLeftAngleQuad);
            _driveTrain.UpdateTelemetry();

            final float COLLECTOR_POWER = 0.8f;
            boolean buttonCollectorToggle = gamepad1.a;

            boolean collectorCooldownComplete = getRuntime() > collectorCooldown;

            if (buttonCollectorToggle && collectorCooldownComplete)
            {
                collectorToggle = !collectorToggle;

                collectorCooldown = getRuntime() + 0.15;
            }

            if (collectorToggle)
            {
                Motors.Collector().SetPower(COLLECTOR_POWER);
            }
            else
            {
                Motors.Collector().SetPower(0);
            }

            telemetry.addData("Collector", collectorToggle);
            telemetry.update();
            idle();
        }
    }
}
