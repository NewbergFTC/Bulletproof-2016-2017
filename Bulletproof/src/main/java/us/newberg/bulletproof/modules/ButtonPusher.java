package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * FTC team 6712 Bulletproof
 */
public class ButtonPusher
{
    public static float LEFT_DEPLOY_POS = 0.7f;
    public static float LEFT_CLOSE_POS = 0.2f;
    public static float RIGHT_DEPLOY_POS = 0.7f;
    public static float RIGHT_CLOSE_POS = 0.2f;

    private enum State
    {
        DEPLOYED,
        CLOSED
    }

    private Servo _servoLeft;
    private Servo _servoRight;

    private State _stateLeft;
    private State _stateRight;
    
    private Telemetry.Item _teleLeftPos;
    private Telemetry.Item _teleLeftState;
    private Telemetry.Item _teleRightPos;
    private Telemetry.Item _teleRightState;

    public ButtonPusher(Servo left, Servo right, Telemetry telemetry)
    {
        _servoLeft = left;
        _servoRight = right;

        _servoLeft.setPosition(LEFT_CLOSE_POS);
        _servoRight.setPosition(RIGHT_CLOSE_POS);

        _stateLeft = State.CLOSED;
        _stateRight = State.CLOSED;
        
        _teleLeftPos = telemetry.addData("Pusher Left:", _servoLeft.getPosition());
        _teleLeftState = telemetry.addData("Pusher Left:", _stateLeft);

        _teleRightPos = telemetry.addData("Pusher Right:", _servoRight.getPosition());
        _teleRightState = telemetry.addData("Pusher Right:", _stateRight);
    }

    public void DeployLeft()
    {
        _servoLeft.setPosition(LEFT_DEPLOY_POS);
        _stateLeft = State.DEPLOYED;
    }

    public void DeployRight()
    {
        _servoRight.setPosition(RIGHT_DEPLOY_POS);
        _stateRight = State.DEPLOYED;
    }

    public void CloseLeft()
    {
        _servoLeft.setPosition(LEFT_CLOSE_POS);
        _stateLeft = State.CLOSED;
    }

    public void CloseRight()
    {
        _servoRight.setPosition(RIGHT_CLOSE_POS);
        _stateRight = State.CLOSED;
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

    public void UpdateTelemetry()
    {
        _teleLeftPos.setValue(_servoLeft.getPosition());
        _teleLeftState.setValue(_stateLeft);

        _teleRightPos.setValue(_servoRight.getPosition());
        _teleRightState.setValue(_stateRight);
    }
}
