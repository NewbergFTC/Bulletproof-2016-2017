package us.newberg.bulletproof.motors;

import com.qualcomm.robotcore.hardware.DcMotor;

import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
public class Flipper extends Motor
{
    public static float GEAR_RATIO = 7.2f;

    public enum State
    {
        NOTHING,
        MANUAL,
        AUTO
    }

    private float _targetTicks;
    private Flipper.State _state;

    public Flipper(DcMotor motor)
    {
        super(motor);
    }

    public Flipper(DcMotor motor, boolean hasEncoder)
    {
        super(motor, hasEncoder);
    }

    public void StartAutoMove()
    {
        float targetTicks = (float) GetCurrentTicks() + ((float)Motors.TICKS_PER_ROTATION * GEAR_RATIO);

        _targetTicks = targetTicks;
        _state = State.AUTO;
    }

    @Override
    public void SetPower(double power)
    {
        _state = State.NOTHING;

        super.SetPower(power);
    }

    public void Update()
    {
        if (_state == State.AUTO)
        {
            int currentTicks = GetCurrentTicks();

            if (currentTicks < _targetTicks)
            {
                SetPower(1.0f);
            }
            else
            {
                SetPower(0);
                _state = State.NOTHING;
            }
        }
    }

    public Flipper.State GetState()
    {
        return _state;
    }
}
