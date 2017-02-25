package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;
import us.or.k12.newberg.newbergcommon.math.Vector2f;
import us.or.k12.newberg.newbergcommon.math.MathUtil;
import us.or.k12.newberg.newbergcommon.vuforia.VuforiaUtil;

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

        _lightSensor.DisableLED();
        _lightSensor.Close();
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
            boolean autoPress = gamepad1.a;

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
                _leftDrivePower.x = powerX;
                _leftDrivePower.y = powerX;

                _rightDrivePower.x = powerX;
                _rightDrivePower.y = powerX;
            }

            if (autoPress)
            {
                for (VuforiaTrackableDefaultListener listener : _listeners)
                {
                    if (listener.isVisible())
                    {
                        _buttonPusher.DeployLeft();
                        _buttonPusher.DeployRight();

                        VectorF angles = VuforiaUtil.AnglesFromTarget(listener);
                        VectorF translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                                Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

                        Vector2f leftPower = new Vector2f();
                        Vector2f rightPower = new Vector2f();

                        final float horizontalPower = 0.08f;
                        final float forwardPower = 0.13f;

                        float[] poseData = poseData = listener.getRawPose().getData();

                        while (opModeIsActive() && !gamepad1.b)
                        {
                            if (listener.getPose() != null)
                            {
                                try
                                {
                                    translation = VuforiaUtil.NavOffWall(listener.getPose().getTranslation(),
                                            Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

                                    angles = VuforiaUtil.AnglesFromTarget(listener);

                                    poseData = listener.getRawPose().getData();
                                }
                                catch (Exception e)
                                {
                                    telemetry.addData("HOLY FUCK", "WE'RE DEAD");
                                }
                            }

                            if (listener.isVisible())
                            {
                                if (translation.get(0) < -540)
                                {
                                    leftPower.x = horizontalPower;
                                    leftPower.y = horizontalPower;

                                    rightPower.x = horizontalPower;
                                    rightPower.y = horizontalPower;
                                }
                                else if (translation.get(0) > -460)
                                {
                                    leftPower.x = -horizontalPower;
                                    leftPower.y = -horizontalPower;

                                    rightPower.x = -horizontalPower;
                                    rightPower.y = -horizontalPower;
                                }
                                else if (poseData[1] < 0.935f)
                                {
                                    if (poseData[2] > 0)
                                    {
                                        // Right
                                        leftPower.x = horizontalPower;
                                        leftPower.y = horizontalPower;

                                        rightPower.x = -horizontalPower;
                                        rightPower.y = -horizontalPower;
                                    }
                                    else
                                    {
                                        // Left
                                        leftPower.x = -horizontalPower;
                                        leftPower.y = -horizontalPower;

                                        rightPower.x = horizontalPower;
                                        rightPower.y = horizontalPower;
                                    }
                                }
                                else
                                {
                                    leftPower.x = -forwardPower;
                                    leftPower.y = forwardPower;

                                    rightPower.x = -forwardPower;
                                    rightPower.y = forwardPower;
                                }
                            }
                            else
                            {
                                break;
                            }

                            _driveTrain.Drive(leftPower, rightPower);

                            idle();
                        }

                        _driveTrain.StopAll();

                        _driveTrain.Drive(Direction.WEST, 0.5f, 5, 750, this);
                    }
                }
            }

            _driveTrain.Drive(_leftDrivePower, _rightDrivePower);
            telemetry.addData("Left angle and quad", "%f, %d", gamepadOneLeftAngle, gamepadOneLeftAngleQuad);

            //
            // Gamepad 2
            //

            final float FLIPPER_POWER   = 1.0f;
            final float COLLECTOR_POWER = 1.0f;

            final boolean buttonCollectorPush     = (gamepad2.right_trigger > 0);
            final boolean buttonCollectorPull     = (gamepad2.left_trigger > 0);
            final boolean buttonPusherLeftToggle  = gamepad2.left_bumper;
            final boolean buttonPusherRightToggle = gamepad2.right_bumper;
            final boolean buttonFlipper           = gamepad2.a;
            final boolean buttonFlipperAuto       = gamepad2.b;
            final boolean buttonHopperDoorQuick   = gamepad2.x;
            final boolean buttonHopperDoorUp      = gamepad2.dpad_up;
            final boolean buttonHopperDoorDown    = gamepad2.dpad_down;

            if (buttonHopperDoorQuick)
            {
                _hopper.QuickDrop();
            }
            else if (buttonHopperDoorDown)
            {
                _hopper.LowerDoor();
            }
            else if (buttonHopperDoorUp)
            {
                _hopper.RaiseDoor();
            }

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
