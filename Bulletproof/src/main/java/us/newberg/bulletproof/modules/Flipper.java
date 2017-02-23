package us.newberg.bulletproof.modules;

import com.elvishew.xlog.XLog;
import com.qualcomm.robotcore.hardware.DcMotor;

import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;
import us.or.k12.newberg.newbergcommon.WatchDog;


/**
 * FTC team 6712 Bulletproof
 */
public class Flipper
{
    public static final String TAG = "Flipper";

    public static final float GEAR_RATIO = 20.0f / 3.0f; // 6 2/3
    public static final float HALF_GEAR_RATIO = GEAR_RATIO / 2; // One flick

    public enum State
    {
        NOTHING,
        MANUAL,
        AUTO
    }

    private Flipper.State _state;
    private FlipperHelper _helper;

    private DcMotor _flipperMotor;

    private WatchDog _watchDog;

    public Flipper(DcMotor motor)
    {
        _helper = new FlipperHelper(this);
        _flipperMotor = motor;
        _watchDog = new WatchDog();
    }

    public void AutoMoveBlocking(BulletproofOpMode caller) throws InterruptedException
    {
        XLog.tag(TAG).d("Auto-move Blocking Start");

        _helper.SetTask(HelperTask.STOP);
        _watchDog.Watch(_helper, 15000);

        StartAutoMove();

        while (GetState() == Flipper.State.AUTO)
        {
            caller.sleep(1);
            caller.Update();
        }

        _watchDog.Stop();

        XLog.tag(TAG).d("Auto-move Blocking Stop");
    }

    public void StartAutoMove()
    {
        XLog.tag(TAG).d("Auto-move Start");

        final float targetTicks = (float) _flipperMotor.getCurrentPosition() + ((float)Motors.TICKS_PER_ROTATION * HALF_GEAR_RATIO * 1.6f);

        _state = State.AUTO;

        new Thread(new Runnable()
        {
         	@Override
            public void run()
            {
                while (_flipperMotor.getCurrentPosition() < targetTicks)
                {
                                   SetPower(1.0f);

        			_state = State.AUTO;
                }

                SetPower(0.0f);
                _state = State.NOTHING;

                XLog.tag(TAG).d("Auto-move Stop");
            }
        }).start();
    }

    public void SetPower(double power)
    {
        XLog.tag(TAG).d("Set power to " + String.valueOf(power));

        _flipperMotor.setPower(power);
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
