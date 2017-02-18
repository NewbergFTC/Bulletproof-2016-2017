package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

@TeleOp(name = "Light Sensor Test", group = "Test")
public class LightSensorOpMode extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        super.Init();
    }

    @Override
    protected void Run() throws InterruptedException
    {
        _lightSensor.Deploy();
        _lightSensor.EnableLED();

        _driveTrain.Drive(Direction.SOUTH, 0.5f, 11f, 2000, this);

        idle();
        sleep(500);
        idle();

        _driveTrain.Drive(new Vector2f(-0.5f, 0), new Vector2f(0, 0.5f));

        while (!_lightSensor.FoundLine())
        {
            telemetry.addData("Light", _lightSensor.GetLight());
            telemetry.addData("Found?", _lightSensor.FoundLine());

            telemetry.update();
            idle();
        }

        _driveTrain.StopAll();

        _flipper.AutoMoveBlocking(this);
        _hopper.QuickDrop();
        _flipper.AutoMoveBlocking(this);

        _driveTrain.Drive(Direction.SOUTH_WEST, 0.3f, 4.5f, 750, this);

        while (!_wheelsListener.isVisible())
        {
            telemetry.addData("ME NO SEE", "FUCKIN FUCK");
            telemetry.update();

            _driveTrain.Drive(new Vector2f(-0.5f, -0.5f), new Vector2f(0.5f, 0.5f));
            sleep(250);
            _driveTrain.StopAll();

            idle();
        }

        AnalyzeAndDeployBlue(_wheelsListener);
        GetToBeacon(_wheelsListener);

        _driveTrain.Drive(Direction.WEST, 0.3f, 5, 500, this);
        _driveTrain.Drive(Direction.EAST, 0.3f, 5, 500, this);

        _buttonPusher.CloseLeft();
        _buttonPusher.CloseRight();

//        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 36, 4000, this);
//        _driveTrain.Drive(new Vector2f(-0.5f, 0), new Vector2f(0, 0.5f));
//
//        while (!_lightSensor.FoundLine())
//        {
//            telemetry.addData("Light", _lightSensor.GetLight());
//            telemetry.addData("Found?", _lightSensor.FoundLine());
//
//            telemetry.update();
//            idle();
//        }

        _driveTrain.StopAll();
    }
}
