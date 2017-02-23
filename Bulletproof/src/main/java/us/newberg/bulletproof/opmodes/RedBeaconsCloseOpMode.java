package us.newberg.bulletproof.opmodes;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

public class RedBeaconsCloseOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _lightSensor.Deploy();
        _lightSensor.EnableLED();

        _driveTrain.Drive(Direction.NORTH, 0.5f, 11f, 2000, this);

        idle();
        sleep(500);
        idle();

        _driveTrain.Drive(new Vector2f(0, 0.5f), new Vector2f(-0.5f, 0));

        while (!_lightSensor.FoundLine())
        {
            idle();
        }

        _lightSensor.Close();
        _lightSensor.DisableLED();
        _driveTrain.StopAll();

        sleep(250);

        _driveTrain.Drive(Direction.WEST, 0.3f, 5, 750, this);

        _flipper.AutoMoveBlocking(this);
        _hopper.QuickDrop();
        _flipper.AutoMoveBlocking(this);

        _driveTrain.Drive(Direction.EAST, 0.3f, 5, 750, this);
        _driveTrain.Drive(Direction.NORTH_EAST, 0.3f, 4.5f, 750, this);

        while (!_gearsListener.isVisible())
        {
            _driveTrain.Drive(new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
            sleep(750);
            _driveTrain.StopAll();

            idle();
        }

        _driveTrain.StopAll();

        AnalyzeAndDeployBlue(_gearsListener);
        GetToBeacon(_gearsListener);

        _driveTrain.Drive(Direction.WEST, 0.3f, 7, 750, this);
        _driveTrain.Drive(Direction.EAST, 0.3f, 5, 750, this);

        _buttonPusher.CloseLeft();
        _buttonPusher.CloseRight();

        _lightSensor.Deploy();
        _lightSensor.EnableLED();

        _driveTrain.Drive(Direction.NORTH_EAST, 0.5f, 36, 4000, this);
        _driveTrain.Drive(new Vector2f(0, 0.5f), new Vector2f(-0.5f, 0));

        while (!_lightSensor.FoundLine())
        {
            idle();
        }

        _lightSensor.Close();
        _lightSensor.DisableLED();
        _driveTrain.StopAll();

        _driveTrain.Drive(Direction.EAST, 0.3f, 2, 750, this);

        while (!_toolsListener.isVisible())
        {
            _driveTrain.Drive(Direction.EAST, 0.3f, 3, 500, this);

            sleep(500);

            idle();
        }

        sleep(500);

        StraightenToBeacon(_toolsListener);
        AnalyzeAndDeployBlue(_toolsListener);
        GetToBeacon(_toolsListener);

        _driveTrain.Drive(Direction.WEST, 0.3f, 7, 750, this);
        _driveTrain.Drive(Direction.EAST, 0.3f, 2, 750, this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (opModeIsActive())
                {
                    _buttonPusher.ToggleLeft();
                    _buttonPusher.ToggleRight();
                    sleep(500);
                }
            }
        }).start();

        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 60, 2500, this);

        _driveTrain.StopAll();
    }
}
