package us.newberg.bulletproof;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static us.newberg.bulletproof.lib.Motors.*;
import us.newberg.bulletproof.math.MathUtil;
import us.newberg.bulletproof.math.Vector2f;

/**
 * FTC team 6712 Bulletproof
 */

public class DriveTrain
{
    private DriveTrainHelper _driveTrainHelper;

    public DriveTrain()
    {
        FrontLeft().setPower(0);
        FrontRight().setPower(0);
        BackLeft().setPower(0);
        BackRight().setPower(0);

        _driveTrainHelper = new DriveTrainHelper(this);
    }

    /**
     * Stop all drive train motors
     */
    public void StopAll()
    {
        FrontLeft().setPower(0);
        FrontRight().setPower(0);
        BackLeft().setPower(0);
        BackRight().setPower(0);
    }

    /**
     *  The the power of the left side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetLeftSidePower()
    {
        Vector2f result = new Vector2f((float) FrontLeft().getPower(), (float) BackLeft().getPower());

        return result;
    }

    /**
     *  The the power of the right side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetRightSidePower()
    {
        Vector2f result = new Vector2f((float) FrontRight().getPower(), (float) BackRight().getPower());

        return result;
    }

    public void DriveStraight(float power, float inches)
    {
        final float TICKS_TO_MOVE = inches * INCHES_TO_TICKS;
        float currentTicks = FrontLeft().getCurrentPosition();
        final float TARGET_TICKS = currentTicks + TICKS_TO_MOVE;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        // TODO(Garrison): Calculate how long it should take to rotate some distance at some power
        WatchDog.Watch(_driveTrainHelper, 10000);

        // TODO(Garrison): See how precise we can make this
        while (MathUtil.FuzzyEquals(currentTicks, TARGET_TICKS, 10)) // Give or take 10 ticks
        {
            if (TARGET_TICKS > currentTicks)
            {
                // TODO(Garrison): Make this able to compensate for wheel spin
                Drive(power, power);
            }
            else if (TARGET_TICKS < currentTicks)
            {
                Drive(-power, -power);
            }
            else
            {
                break;
            }

            currentTicks = FrontLeft().getCurrentPosition();
        }

        WatchDog.Stop();

        FrontLeft().setPower(0);
        BackRight().setPower(0);
        FrontRight().setPower(0);
        BackRight().setPower(0);
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
        // TODO(Garrison): Should we just pass a left float and a right float?

        FrontLeft().setPower(leftSidePower.x);
        BackLeft().setPower(leftSidePower.y);
        FrontRight().setPower(rightSidePower.x);
        BackRight().setPower(rightSidePower.y);
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
        final float TICKS_TO_MOVE = deg * DEG_TO_TICKS;
        float currentTicks = FrontLeft().getCurrentPosition();
        final float TARGET_TICKS = currentTicks + TICKS_TO_MOVE;

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

            currentTicks = FrontLeft().getCurrentPosition();
        }

        WatchDog.Stop();

        FrontLeft().setPower(0);
        BackRight().setPower(0);
        FrontRight().setPower(0);
        BackRight().setPower(0);
    }

    // TODO(Peacock): A {@link Telemetry} updater/display

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
