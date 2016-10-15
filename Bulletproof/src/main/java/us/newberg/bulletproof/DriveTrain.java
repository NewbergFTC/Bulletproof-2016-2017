package us.newberg.bulletproof;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static us.newberg.bulletproof.lib.Motors.*;
import us.newberg.bulletproof.math.MathUtil;
import us.newberg.bulletproof.math.Vector2f;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */

public class DriveTrain
{
    private Telemetry.Item _teleFrontLeft;
    private Telemetry.Item _teleFrontRight;
    private Telemetry.Item _teleBackRight;
    private Telemetry.Item _teleBackLeft;

    private DriveTrainHelper _driveTrainHelper;

    private boolean _awdEnabled;

    public DriveTrain(Telemetry telemetry)
    {
        this(telemetry, false);
    }

    public DriveTrain(Telemetry telemetry, boolean awd)
    {
        FrontLeft().SetPower(0);
        FrontRight().SetPower(0);
        BackLeft().SetPower(0);
        BackRight().SetPower(0);

        _teleFrontLeft  = telemetry.addData("FL Wheel", "%.2f", FrontLeft().GetPower());
        _teleFrontRight = telemetry.addData("FR Wheel", "%.2f", FrontRight().GetPower());
        _teleBackLeft   = telemetry.addData("BL Wheel", "%.2f", BackLeft().GetPower());
        _teleBackRight  = telemetry.addData("BR Wheel", "%.2f", BackRight().GetPower());

        _driveTrainHelper = new DriveTrainHelper(this);
        _awdEnabled = awd;
    }

    /**
     * Stop all drive train motors
     */
    public void StopAll()
    {
        FrontLeft().SetPower(0);
        FrontRight().SetPower(0);
        BackLeft().SetPower(0);
        BackRight().SetPower(0);
    }

    /**
     *  The the power of the left side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetLeftSidePower()
    {
        Vector2f result = new Vector2f((float) FrontLeft().GetPower(), (float) BackLeft().GetPower());
        return result;
    }

    /**
     *  The the power of the right side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetRightSidePower()
    {
        Vector2f result = new Vector2f((float) FrontRight().GetPower(), (float) BackRight().GetPower());
        return result;
    }

    public void DriveStraight(float power, float inches, Telemetry telemetry, BulletproofOpMode caller)
            throws InterruptedException
    {
        final double TICKS_TO_MOVE = inches * TICKS_TO_INCHES * WHEEL_DISTANCE_CORRECTION;
        final float derivedPower = MathUtil.Mapf(power, 0, 1, 0, 0.9f);

        Vector2f leftPower = new Vector2f(derivedPower);
        Vector2f rightPower = new Vector2f(derivedPower);

        FrontLeft().ResetTicks();
        FrontRight().ResetTicks();
        BackLeft().ResetTicks();
        BackRight().ResetTicks();

        float frontLeftTicks = FrontLeft().GetCurrentTicks();
        float frontRightTicks = FrontRight().GetCurrentTicks();
        float backLeftTicks = BackLeft().GetCurrentTicks();
        float backRightTicks = BackRight().GetCurrentTicks();

        float avgTicks = (frontLeftTicks + frontRightTicks + backLeftTicks + backRightTicks) / 4;

        final double TARGET_TICKS = TICKS_TO_MOVE;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some distance at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        while (!MathUtil.FuzzyEquals(avgTicks, TARGET_TICKS, 40))
        {
            frontLeftTicks = FrontLeft().GetCurrentTicks();
            frontRightTicks = FrontRight().GetCurrentTicks();
            backLeftTicks = BackLeft().GetCurrentTicks();
            backRightTicks = BackRight().GetCurrentTicks();

            avgTicks = (frontLeftTicks + frontRightTicks + backLeftTicks + backRightTicks) / 4;

            float epsilon = Math.abs(avgTicks) + 35;

            MathUtil.Range frontLeftRange = MathUtil.ValueInRange(frontLeftTicks,
                                                                  avgTicks - epsilon, avgTicks + epsilon);
            MathUtil.Range frontRightRange = MathUtil.ValueInRange(frontRightTicks,
                                                                   avgTicks - epsilon, avgTicks + epsilon);
            MathUtil.Range backLeftRange = MathUtil.ValueInRange(backLeftTicks,
                                                                 avgTicks - epsilon, avgTicks + epsilon);
            MathUtil.Range backRightRange = MathUtil.ValueInRange(backRightTicks,
                                                                  avgTicks - epsilon, avgTicks + epsilon);

            switch (frontLeftRange)
            {
                case OVER:
                        leftPower.x -= 0.1f;
                    break;

                case UNDER:
                        leftPower.x += 0.1f;
                    break;

                default:
                case WITHIN:
                    break;
            }

            switch (backLeftRange)
            {
                case OVER:
                    leftPower.y -= 0.1f;
                    break;

                case UNDER:
                    leftPower.y += 0.1f;
                    break;

                default:
                case WITHIN:
                    break;
            }

            switch (frontRightRange)
            {
                case OVER:
                    rightPower.x -= 0.1f;
                    break;

                case UNDER:
                    rightPower.x += 0.1f;
                    break;

                default:
                case WITHIN:
                    break;
            }

            switch (backRightRange)
            {
                case OVER:
                    rightPower.y -= 0.1f;
                    break;

                case UNDER:
                    rightPower.x += 0.1f;
                    break;

                default:
                case WITHIN:
                    break;
            }

            if (TARGET_TICKS < frontLeftTicks)
            {
                Drive(leftPower.Mul(-1), rightPower);
            }
            else if (TARGET_TICKS > frontLeftTicks)
            {
                Drive(leftPower, rightPower.Mul(-1));
            }
            else
            {
                break;
            }

            caller.telemetry.addData("FL Ticks: ", FrontLeft().GetCurrentTicks());
            caller.telemetry.addData("FR Ticks: " , FrontRight().GetCurrentTicks());
            caller.telemetry.addData("BL Ticks: ", BackLeft().GetCurrentTicks());
            caller.telemetry.addData("BR Ticks: ", BackRight().GetCurrentTicks());
            caller.telemetry.update();
            caller.idle();
        }

        WatchDog.Stop();

        FrontLeft().SetPower(0);
        BackRight().SetPower(0);
        FrontRight().SetPower(0);
        BackRight().SetPower(0);
    }

    /**
     * Drive with the whole left side at the same power, and the whole right at another constant power
     *
     * The power must be between -1.0 and 1.0
     *
     * @param leftPower The power for the left wheels
     * @param rightPower The power for the right wheels
     */
    public void Drive(float leftPower, float rightPower)
    {
        Drive(new Vector2f(leftPower), new Vector2f(rightPower));
    }

    /**
     * Drive with a left side power and a right side power
     *
     * The x is the front wheel power
     * The y is the back wheel power
     *
     * @param leftSidePower  The x component is the front wheel power, and the y component the back wheel power
     * @param rightSidePower The x component is the front wheel power, and the y component the back wheel power
     */
    public void Drive(Vector2f leftSidePower, Vector2f rightSidePower)
    {
        FrontLeft().SetPower(leftSidePower.x);
        BackLeft().SetPower(leftSidePower.y);
        FrontRight().SetPower(rightSidePower.x);
        BackRight().SetPower(rightSidePower.y);
    }

    /**
     * Rotate the front of the robot by {@param deg}
     *
     * Currently doesn't convert between deg and encoder ticks, but will in the future
     *
     * @throws InterruptedException For ftc_app to catch, if something should go <em>very</em> wrong
     * @param deg The angle to rotate in degrees
     * @param power The target power to turn at 1.0 is full power forward, -1.0 is full power back(inverts {@param deg})
     */
    public void Rotate(float power, float deg, BulletproofOpMode caller) throws InterruptedException
    {
        final double TICKS_TO_MOVE = deg * DEG_TO_TICKS;
        float currentTicks = FrontLeft().GetCurrentTicks();
        final double TARGET_TICKS = currentTicks + TICKS_TO_MOVE;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some ticks(or degrees) at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        while (MathUtil.FuzzyEquals(currentTicks, TARGET_TICKS, 10)) // Give or take 10 ticks
        {
            if (TARGET_TICKS > currentTicks)
            {
                Drive(power, -power);
            }
            else if (TARGET_TICKS < currentTicks)
            {
               Drive(-power, power);
            }
            else
            {
                break;
            }

            currentTicks = FrontLeft().GetCurrentTicks();
            caller.idle();
        }

        WatchDog.Stop();

        FrontLeft().SetPower(0);
        BackRight().SetPower(0);
        FrontRight().SetPower(0);
        BackRight().SetPower(0);
    }

    /**
     * Display telemetry data
     *
     * Displays the power to each wheel
     */
    public void UpdateTelemetry()
    {
        _teleFrontLeft.setValue("%.2f",  FrontLeft().GetPower());
        _teleFrontRight.setValue("%.2f", FrontRight().GetPower());
        _teleBackLeft.setValue("%.2f", BackLeft().GetPower());
        _teleBackRight.setValue("%.2f", BackRight().GetPower());
    }

    public void SetAWD(boolean awd)
    {
        _awdEnabled = awd;
    }

    public boolean GetAWDEnabled()
    {
        return _awdEnabled;
    }

    private enum HelperTask
    {
        NONE,
        STOP_ALL,
    }

    private static final int HELPER_TASK_COUNT = HelperTask.values().length;

    private class DriveTrainHelper implements Runnable
    {
        private DriveTrain _driveTrain;
        private HelperTask _task;

        private DriveTrainHelper(DriveTrain target)
        {
            _driveTrain = target;
            _task = HelperTask.NONE;
        }

        private void SetTask(HelperTask task)
        {
            _task = task;
        }

        private void StopAll()
        {
            _driveTrain.StopAll();
        }

        @Override
        public void run()
        {
            switch (_task)
            {
                default:
                case NONE:
                    break;

                case STOP_ALL:
                    StopAll();
                    break;
            }
        }
    }
}
