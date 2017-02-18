package us.newberg.bulletproof.modules;

import com.elvishew.xlog.XLog;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * FTC team 6712 Bulletproof
 */
public class ButtonPusher
{
    public static final String TAG = "ButtonPusher";

    public static float LEFT_DEPLOY_POS = 1f;
    public static float LEFT_CLOSE_POS = 0f;
    public static float RIGHT_DEPLOY_POS = 1f;
    public static float RIGHT_CLOSE_POS = 0f;

    private enum State
    {
        DEPLOYED,
        CLOSED
    }

    private Servo _servoLeft;
    private Servo _servoRight;

    private State _stateLeft;
    private State _stateRight;

    public ButtonPusher(Servo left, Servo right)
    {
        _servoLeft = left;
        _servoRight = right;

        _servoLeft.setPosition(LEFT_CLOSE_POS);
        _servoRight.setPosition(RIGHT_CLOSE_POS);

        _stateLeft = State.CLOSED;
        _stateRight = State.CLOSED;

        XLog.tag(TAG).d("Initialized");
    }

    public void DeployLeft()
    {
        _servoLeft.setPosition(LEFT_DEPLOY_POS);
        _stateLeft = State.DEPLOYED;

        XLog.tag(TAG).d("Deploy Left");
    }

    public void DeployRight()
    {
        _servoRight.setPosition(RIGHT_DEPLOY_POS);
        _stateRight = State.DEPLOYED;

        XLog.tag(TAG).d("Deploy Right");
    }

    public void CloseLeft()
    {
        _servoLeft.setPosition(LEFT_CLOSE_POS);
        _stateLeft = State.CLOSED;

        XLog.tag(TAG).d("Close Left");
    }

    public void CloseRight()
    {
        _servoRight.setPosition(RIGHT_CLOSE_POS);
        _stateRight = State.CLOSED;

        XLog.tag(TAG).d("Close Right");
    }

    public void ToggleLeft()
    {
        if (_stateLeft == State.CLOSED)
        {
            DeployLeft();
        }
        else
        {
            CloseLeft();
        }
    }

    public void ToggleRight()
    {
        if (_stateRight == State.CLOSED)
        {
            DeployRight();
        }
        else
        {
            CloseRight();
        }
    }
}
