package us.newberg.bulletproof;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import us.newberg.bulletproof.lib.Motors;
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

    public DriveTrain(Telemetry telemetry)
    {
        Motors.FrontLeft.SetPower(0);
        Motors.FrontRight.SetPower(0);
        Motors.BackLeft.SetPower(0);
        Motors.BackRight.SetPower(0);

        _teleFrontLeft  = telemetry.addData("FL Wheel", "%.2f", Motors.FrontLeft.GetPower());
        _teleFrontRight = telemetry.addData("FR Wheel", "%.2f", Motors.FrontRight.GetPower());
        _teleBackLeft   = telemetry.addData("BL Wheel", "%.2f", Motors.BackLeft.GetPower());
        _teleBackRight  = telemetry.addData("BR Wheel", "%.2f", Motors.BackRight.GetPower());

        _driveTrainHelper = new DriveTrainHelper(this);
    }

    /**
     * Stop all drive train motors
     */
    public void StopAll()
    {
        Motors.FrontLeft.SetPower(0);
        Motors.FrontRight.SetPower(0);
        Motors.BackLeft.SetPower(0);
        Motors.BackRight.SetPower(0);
    }

    /**
     *  The the power of the left side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetLeftSidePower()
    {
        Vector2f result = new Vector2f((float) Motors.FrontLeft.GetPower(), (float) Motors.BackLeft.GetPower());
        return result;
    }

    /**
     *  The the power of the right side wheels
     *
     * @return The x is the front power and the y is the back power
     */
    public Vector2f GetRightSidePower()
    {
        Vector2f result = new Vector2f((float) Motors.FrontRight.GetPower(), (float) Motors.BackRight.GetPower());
        return result;
    }

    private double CalculateTicksToMove(float inches, boolean comp)
    {
        double result = inches * Motors.TICKS_TO_INCHES;

        if (comp)
        {
            result *= Motors.WHEEL_DISTANCE_CORRECTION;
        }

        return Math.abs(result);
    }

    private int GetCurrentTicks(Direction direction)
    {
        int result;

        if (direction == Direction.NORTH_WEST || direction == Direction.SOUTH_EAST)
        {
            result = Motors.FrontRight.GetCurrentTicks();
        }
        else
        {
            result = Motors.FrontLeft.GetCurrentTicks();
        }

        return result;
    }

    // TODO(Garrison): Testing
    public void Drive(Direction direction, float power, float inches, long maxDuration, BulletproofOpMode caller)
            throws InterruptedException
    {
        boolean complete = false;

        StopAll();

        float reverse = 1;
        boolean comp = true;

        _driveTrainHelper.SetTask(HelperTask.STOP_ALL);

        Vector2f leftPower = new Vector2f();
        Vector2f rightPower = new Vector2f();

        // TODO(Garrison): Move this to its own function
        switch (direction)
        {
            case NORTH:
                leftPower.y = power;
                leftPower.x = power;

                rightPower.y = -power;
                rightPower.x = -power;

                reverse = 1;
                comp = true;
                break;
            case NORTH_EAST:
                leftPower.x = power;
                leftPower.y = 0;

                rightPower.x = 0;
                rightPower.y = -power;

                reverse = 1;
                comp = false;
                break;
            case EAST:
                leftPower.x = power;
                leftPower.y = -power;

                rightPower.x = power;
                rightPower.y = -power;

                reverse = 1;
                comp = true;
                break;
            case SOUTH_EAST:
                leftPower.x = 0;
                leftPower.y = -power;

                rightPower.x = power;
                rightPower.y = 0;

                reverse = 1;
                comp = false;
                break;
            case SOUTH:
                leftPower.y = -power;
                leftPower.x = -power;

                rightPower.y = power;
                rightPower.x = power;

                comp = true;
                reverse = -1;
                break;
            case SOUTH_WEST:
                leftPower.x = -power;
                leftPower.y = 0;

                rightPower.x = 0;
                rightPower.y = power;

                reverse = -1;
                comp = false;
                break;
            case WEST:
                leftPower.x = -power;
                leftPower.y = power;

                rightPower.x = -power;
                rightPower.y = power;

                reverse = -1;
                comp = true;
                break;
            case NORTH_WEST:
                leftPower.x = 0;
                leftPower.y = power;

                rightPower.x = -power;
                rightPower.y = 0;

                reverse = -1;
                comp = false;
                break;
        }

        int currentTicks = GetCurrentTicks(direction);
        double targetTicks = (double) currentTicks + (CalculateTicksToMove(inches, comp) * reverse);

        WatchDog.Watch(_driveTrainHelper, maxDuration);
        while (!complete)
        {
            currentTicks = GetCurrentTicks(direction);

            // TODO(Garrison): Find some better way to tell when we have arrived
            if ((reverse == -1) && (currentTicks <= targetTicks))
            {
                complete = true;
                break;

            }
            else if ((reverse != -1) && (currentTicks >= targetTicks))
            {
                complete = true;
                break;
            }

            if (_driveTrainHelper.GetTask() == HelperTask.COMPLETE)
            {
                complete = true;
                break;
            }

            Drive(leftPower, rightPower);

            caller.telemetry.addData("Power", power);
            caller.telemetry.addData("Current Ticks", currentTicks);
            caller.telemetry.addData("Target Ticks", targetTicks);
            caller.telemetry.update();

            // TODO(Garrison): Maybe handle sleeping ourselves, so we can check the ticks more often
            caller.waitOneFullHardwareCycle();
        }

        WatchDog.Stop();
        StopAll();
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
        Motors.FrontLeft.SetPower(leftSidePower.x);
        Motors.BackLeft.SetPower(leftSidePower.y);
        Motors.FrontRight.SetPower(rightSidePower.x);
        Motors.BackRight.SetPower(rightSidePower.y);
    }

    /**
     * Display telemetry data
     *
     * Displays the power to each wheel
     */
    public void UpdateTelemetry()
    {
        _teleFrontLeft.setValue("%.2f",  Motors.FrontLeft.GetPower());
        _teleFrontRight.setValue("%.2f", Motors.FrontRight.GetPower());
        _teleBackLeft.setValue("%.2f", Motors.BackLeft.GetPower());
        _teleBackRight.setValue("%.2f", Motors.BackRight.GetPower());
    }

    private enum HelperTask
    {
        NONE,
        COMPLETE,
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

        public HelperTask GetTask()
        {
            return _task;
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
                    SetTask(HelperTask.COMPLETE);
                    break;
            }
        }
    }
}
