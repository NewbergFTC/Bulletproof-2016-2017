package us.newberg.bulletproof.modules;

import com.qualcomm.robotcore.hardware.Servo;

public class Hopper
{
    public static final String TAG = "Hopper";

    public static final float DOOR_DOWN_POS = 1.0f;
    public static final float DOOR_UP_POS = 0.64f;

    private Servo _doorServo;
    private boolean _doorDown;

    public Hopper(Servo servo)
    {
        _doorServo = servo;
        _doorDown = true;
        _doorServo.setPosition(DOOR_DOWN_POS);
    }

    public void QuickDrop()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                _doorServo.setPosition(DOOR_UP_POS);

                try
                {
                    Thread.sleep(400);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                _doorServo.setPosition(DOOR_DOWN_POS);
            }
        }).start();
    }

    public void RaiseDoor()
    {
        _doorServo.setPosition(DOOR_UP_POS);
        _doorDown = false;
    }

    public void LowerDoor()
    {
        _doorServo.setPosition(DOOR_DOWN_POS);
        _doorDown = true;
    }

    public void ToggleDoor()
    {
        if (_doorDown)
        {
            RaiseDoor();
        }
        else
        {
            LowerDoor();
        }
    }
}
