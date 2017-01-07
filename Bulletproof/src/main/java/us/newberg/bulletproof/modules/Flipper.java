package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import us.newberg.bulletproof.WatchDog;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
public class Flipper
{
    public static float GEAR_RATIO = 10.9f;

    public enum State
    {
        NOTHING,
        MANUAL,
        AUTO
    }

    private float _targetTicks;
    private Flipper.State _state;
    private FlipperHelper _helper;

    private DcMotor _flipperMotor;

    private Telemetry.Item _telState;

    public Flipper(DcMotor motor, Telemetry telemetry)
    {
        _helper = new FlipperHelper(this);
        _flipperMotor = motor;
        _telState = telemetry.addData("Flipper State: ", _state);
    }

    public void UpdateMotor(DcMotor motor)
    {
        _flipperMotor = motor;
    }

    public void AutoMoveBlocking(BulletproofOpMode caller) throws InterruptedException
    {
        _helper.SetTask(HelperTask.STOP);
        WatchDog.Watch(_helper, 10000);

        StartAutoMove();

        while (GetState() == Flipper.State.AUTO)
        {
            caller.sleep(1);
            caller.Update();
        }

        WatchDog.Stop();
    }

    public void StartAutoMove()
    {
        float targetTicks = (float) _flipperMotor.getCurrentPosition() + ((float)Motors.TICKS_PER_ROTATION * GEAR_RATIO);

        _targetTicks = targetTicks;
        _state = State.AUTO;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (_flipperMotor.getCurrentPosition() < _targetTicks)
                {
                    SetPower(1.0f);
                }

                SetPower(0);
                _state = State.NOTHING;
            }
        }).run();
    }

    public void SetPower(double power)
    {
        _state = State.NOTHING;
        _flipperMotor.setPower(power);
    }

    public void UpdateTelemetry()
    {
        _telState.setValue(_state);
    }

    public Flipper.State GetState()
    {
        return _state;
    }

    private enum HelperTask
    {
        NONE,
        COMPLETE,
        STOP,
    }

    private class FlipperHelper implements Runnable
    {
        private Flipper _flipper;
        private HelperTask _task;

        private FlipperHelper(Flipper target)
        {
            _flipper = target;
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
            _flipper.SetPower(0);
        }

        @Override
        public void run()
        {
            switch (_task)
            {
                default:
                case NONE:
                    break;

                case STOP:
                    StopAll();
                    SetTask(HelperTask.COMPLETE);
                    break;
            }
        }
    }
}
