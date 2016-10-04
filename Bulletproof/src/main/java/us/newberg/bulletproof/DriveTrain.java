package us.newberg.bulletproof;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static us.newberg.bulletproof.lib.Motors.*;
import us.newberg.bulletproof.math.MathUtil;
import us.newberg.bulletproof.math.Vector2f;

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

    public DriveTrain(Telemetry telemetry)
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

    public void DriveStraight(float power, float inches, Telemetry telemetry)
    {
        final double TICKS_TO_MOVE = inches * TICKS_TO_INCHES;
        telemetry.addData("Ticks to move", TICKS_TO_MOVE);
        telemetry.update();
        float currentTicks = FrontLeft().GetCurrentTicks();
        final double TARGET_TICKS = currentTicks + TICKS_TO_MOVE;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some distance at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        // TODO(Garrison): See how precise we can make this
        while (!MathUtil.FuzzyEquals(currentTicks, TARGET_TICKS, 40)) // Give or take 10 ticks
        {
            if (TARGET_TICKS < currentTicks)
            {
                // TODO(Garrison): Make this able to compensate for wheel spin
                Drive(-power, power);
            }
            else if (TARGET_TICKS > currentTicks)
            {
                Drive(power, -power);
            }
            else
            {
                break;
            }

            currentTicks = FrontLeft().GetCurrentTicks();
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
    public void Rotate(float power, float deg) throws InterruptedException
    {
        final double TICKS_TO_MOVE = deg * DEG_TO_TICKS;
        float currentTicks = FrontLeft().GetCurrentTicks();
        final double TARGET_TICKS = currentTicks + TICKS_TO_MOVE;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some ticks(or degrees) at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        // TODO(Garrison): See how precise we can make this
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
