package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * FTC team 6712 Bulletproof
 */
public class Flipper extends Motor
{
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

    public void StartAutoMove(float target)
    {
        _targetTicks = target;
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
