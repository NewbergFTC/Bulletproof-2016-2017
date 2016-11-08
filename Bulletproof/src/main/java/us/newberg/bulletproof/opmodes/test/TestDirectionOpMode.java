package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "QuickTest", group = "Test")
@Disabled
public class TestDirectionOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        int delay = 1000;
        
        _driveTrain.Drive(Direction.NORTH, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.NORTH_EAST, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.EAST, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.SOUTH, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.SOUTH_WEST, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.WEST, 0.5f, 12.0f, 10000, this);
        sleep(delay);
        _driveTrain.Drive(Direction.NORTH_WEST, 0.5f, 12.0f, 10000, this);
    }
}
