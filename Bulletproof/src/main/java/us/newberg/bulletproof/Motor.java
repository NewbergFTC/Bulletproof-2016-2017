package us.newberg.bulletproof;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * FTC team 6712 Bulletproof
 */

public abstract class Motor implements DcMotor
{
    private boolean _hasEncoder = false;

    public void SetHasEncoder(boolean hasEncoder)
    {
        _hasEncoder = hasEncoder;
    }

    public boolean HasEncoder()
    {
        return _hasEncoder;
    }
}
