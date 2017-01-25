package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.lib.Motors;
import us.or.k12.newberg.newbergcommon.math.Vector2f;
import us.or.k12.newberg.newbergcommon.math.MathUtil;

/**
 * FTC team 6712 Bulletproof
 */
@TeleOp(name = "DriverOpMode", group = "Driver")
public class DriverOpMode extends BulletproofOpMode
{
    private Vector2f _leftDrivePower;
    private Vector2f _rightDrivePower;

    @Override
    protected void Init()
    {
        super.Init();

        _leftDrivePower = new Vector2f();
        _rightDrivePower = new Vector2f();
    }

    @Override
    public void Run() throws InterruptedException
    {
        while(opModeIsActive())
        {
            // TODO(Garrison): Deadzones
            Vector2f gamepadOneLeft = new Vector2f(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            Vector2f gamepadOneRight = new Vector2f(gamepad1.right_stick_x, gamepad1.right_stick_y);

            boolean fineControls = gamepad1.right_bumper;

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

            _leftDrivePower.x = 0;
            _leftDrivePower.y = 0;
            _rightDrivePower.x = 0;
            _rightDrivePower.y = 0;

            float powerY = Math.abs(gamepadOneLeft.y);
            float powerX = Math.abs(gamepadOneLeft.x);

            if (fineControls)
            {
                powerX = MathUtil.Mapf(0.0f, 1.0f, 0.0f, 0.4f, powerX);
                powerY = MathUtil.Mapf(0.0f, 1.0f, 0.0f, 0.4f, powerY);
            }

            switch (gamepadOneLeftAngleQuad)
            {
                case 1:
                    if (gamepadOneLeftAngle >= 70)
                    {
                        // Forward
                        _leftDrivePower.y = powerY;
                        _leftDrivePower.x = powerY;

                        _rightDrivePower.y = -powerY;
                        _rightDrivePower.x = -powerY;
                    }
                    else if ((gamepadOneLeftAngle > 30) && gamepadOneLeftAngle < 70)
                    {
                        // Forward Right
                        _leftDrivePower.x = powerY;
                        _leftDrivePower.y = 0;

                        _rightDrivePower.x = 0;
                        _rightDrivePower.y = -powerY;
                    }
                    else if (gamepadOneLeftAngle <= 30)
                    {
                        // Right
                        _leftDrivePower.x = powerX;
                        _leftDrivePower.y = -powerX;

                        _rightDrivePower.x = powerX;
                        _rightDrivePower.y = -powerX;
                    }
                    break;
                case 2:
                    if (gamepadOneLeftAngle >= 70)
                    {
                        // Forward
                        _leftDrivePower.y = powerY;
                        _leftDrivePower.x = powerY;

                        _rightDrivePower.y = -powerY;
                        _rightDrivePower.x = -powerY;
                    }
                    else if (gamepadOneLeftAngle < 70 && gamepadOneLeftAngle > 30)
                    {
                        // Forward Left
                        _leftDrivePower.x = 0;
                        _leftDrivePower.y = powerY;

                        _rightDrivePower.x = -powerY;
                        _rightDrivePower.y = 0;
                    }
                    else if (gamepadOneLeftAngle <= 30)
                    {
                        // Left
                        _leftDrivePower.x = -powerX;
                        _leftDrivePower.y = powerX;

                        _rightDrivePower.x = -powerX;
                        _rightDrivePower.y = powerX;
                    }
                    break;
                case 3:
                    if (gamepadOneLeftAngle <= 30)
                    {
                        // Left
                        _leftDrivePower.x = -powerX;
                        _leftDrivePower.y = powerX;

                        _rightDrivePower.x = -powerX;
                        _rightDrivePower.y = powerX;
					}
                    else if (gamepadOneLeftAngle > 30 && gamepadOneLeftAngle < 70)
                    {
                        // Back Left
						_leftDrivePower.x = -powerY;
                        _leftDrivePower.y = 0;

                        _rightDrivePower.x = 0;
                        _rightDrivePower.y = powerY;
                    }
                    else if (gamepadOneLeftAngle >= 70)
                    { // Back
                        _leftDrivePower.y = -powerY;
                        _leftDrivePower.x = -powerY;

                        _rightDrivePower.y = powerY;
                        _rightDrivePower.x = powerY;
                    }
                    break;
                case 4:
                    if (gamepadOneLeftAngle <= 30)
                    {
                        // Right
                        _leftDrivePower.x = powerX;
                        _leftDrivePower.y = -powerX;

                        _rightDrivePower.x = powerX;
                        _rightDrivePower.y = -powerX;
                    }
                    else if (gamepadOneLeftAngle > 30 && gamepadOneLeftAngle < 70)
                    {
                        // Back Right
                        _leftDrivePower.x = 0;
                        _leftDrivePower.y = -powerY;

                        _rightDrivePower.x = powerY;
                        _rightDrivePower.y = 0;
                    }
                    else if (gamepadOneLeftAngle >= 70)
                    {
                        // Back
                        _leftDrivePower.y = -powerY;
                        _leftDrivePower.x = -powerY;

                        _rightDrivePower.y = powerY;
                        _rightDrivePower.x = powerY;
                    }
                    break;
            }

            powerX = gamepadOneRight.x;

            if (fineControls)
            {
                powerX = MathUtil.Mapf(-1.0f, 1.0f, -0.2f, 0.2f, powerX);
            }

            if (Math.abs(gamepadOneRight.x) >= 0.1f )
            {
                // Right
                _leftDrivePower.x = powerX;
                _leftDrivePower.y = powerX;

                _rightDrivePower.x = powerX;
                _rightDrivePower.y = powerX;
            }

            _driveTrain.Drive(_leftDrivePower, _rightDrivePower);
            telemetry.addData("Left angle and quad", "%f, %d", gamepadOneLeftAngle, gamepadOneLeftAngleQuad);

            //
            // Gamepad 2
            //

            final float FLIPPER_POWER = 1.0f;
            final float COLLECTOR_POWER = 1.0f;

            boolean buttonCollectorPush = (gamepad2.right_trigger > 0);
            boolean buttonCollectorPull = (gamepad2.left_trigger > 0);
            boolean buttonPusherLeftToggle = gamepad2.left_bumper;
            boolean buttonPusherRightToggle = gamepad2.right_bumper;
            boolean buttonFlipper = gamepad2.a;
            boolean buttonFlipperAuto = gamepad2.b;
            boolean buttonLifterUp = gamepad2.x;
            boolean buttonLifterDown = gamepad2.y;

            if  (buttonCollectorPull)
            {
                _collector.StartPull();
            }
            else if (buttonCollectorPush)
            {
                _collector.StartPush();
            }
            else
            {
                _collector.Stop();
            }

            if (buttonPusherLeftToggle)
            {
                _buttonPusher.DeployLeft();
            }
            else
            {
                _buttonPusher.CloseLeft();
            }

            if (buttonPusherRightToggle)
            {
                _buttonPusher.DeployRight();
            }
            else
            {
                _buttonPusher.CloseRight();
            }

            if (buttonFlipper)
            {
                _flipper.SetPower(FLIPPER_POWER);
            }
            else if (_flipper.GetState() != Flipper.State.AUTO)
            {
                _flipper.SetPower(0);
            }

            if (buttonFlipperAuto)
            {
                _flipper.StartAutoMove();
            }

            _driveTrain.UpdateTelemetry();
            Update();
        }
    }
}
