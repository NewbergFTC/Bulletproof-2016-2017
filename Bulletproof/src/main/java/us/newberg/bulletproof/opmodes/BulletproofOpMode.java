package us.newberg.bulletproof.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtColorSensor;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtUltrasonicSensor;

//import us.newberg.bulletproof.lib.Servos;
import us.newberg.bulletproof.DriveTrain;
import us.newberg.bulletproof.lib.Motors;

public  abstract class BulletproofOpMode extends LinearOpMode
{
    public HiTechnicNxtLightSensor LightSensor;

    public HiTechnicNxtUltrasonicSensor ultrasonicSensor;

    public HiTechnicNxtColorSensor ColorSensor;
    protected  DriveTrain _driveTrain;




    public BulletproofOpMode()
    {
        super();
        // NOTE(Garrison): Don't init any ftc objects here.

        _driveTrain = null;
    }

    public void Init()
    {
//        Servos.Init(hardwareMap);

        Motors.Init(hardwareMap);

        LightSensor = (HiTechnicNxtLightSensor) hardwareMap.lightSensor.get("LightSensor");

        ColorSensor = (HiTechnicNxtColorSensor)hardwareMap.colorSensor.get("ColorSensor");

        ultrasonicSensor = (HiTechnicNxtUltrasonicSensor)hardwareMap.ultrasonicSensor.get("ultraSonicSensor");

        _driveTrain = new DriveTrain(telemetry);
    }

    public boolean lightSensor(HiTechnicNxtLightSensor lightSensor)
    {
        lightSensor.enableLed(true);

        double LightSensorDataRaw = lightSensor.getRawLightDetected();
        telemetry.addData("lightsensorvalue",lightSensor.getRawLightDetected());
        while (lightSensor.getRawLightDetected() < 1.7)
        {
            telemetry.addData("lightsensorvalue",lightSensor.getRawLightDetected());
            LightSensorDataRaw = lightSensor.getRawLightDetected();
            Motors.FrontRight().SetPower(0.5);
            Motors.FrontLeft().SetPower(-0.5);
            Motors.BackRight().SetPower(0.5);
            Motors.BackLeft().SetPower(-0.5);

            if (!opModeIsActive())
                return true;

            telemetry.update();
        }
        Motors.FrontRight().SetPower(0);
        Motors.FrontLeft().SetPower(0);
        Motors.BackRight().SetPower(0);
        Motors.BackLeft().SetPower(0);
        return false;
    }

    public void DriveBackwards (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(0.5);
        Motors.FrontLeft().SetPower(-0.5);
        Motors.BackRight().SetPower(0.5);
        Motors.BackLeft().SetPower(-0.5);
        sleep(time);

    }

    public void DriveForwards (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(-0.5);
        Motors.FrontLeft().SetPower(0.5);
        Motors.BackRight().SetPower(-0.5);
        Motors.BackLeft().SetPower(0.5);
        sleep(time);

    }

    public void DriveLeft (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(-.5);
        Motors.FrontLeft().SetPower(-.5);
        Motors.BackRight().SetPower(.5);
        Motors.BackLeft().SetPower(.5);
        sleep(time);

    }

    public void DriveRight (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(.5);
        Motors.FrontLeft().SetPower(.5);
        Motors.BackRight().SetPower(-.5);
        Motors.BackLeft().SetPower(-.5);
        sleep(time);

    }

    public void TurnClockWise (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(0.5);
        Motors.FrontLeft().SetPower(0.5);
        Motors.BackRight().SetPower(0.5);
        Motors.BackLeft().SetPower(0.5);
        sleep(time);

    }

    public void TurnCounterClockWise (int time) throws InterruptedException{
        Motors.FrontRight().SetPower(-0.5);
        Motors.FrontLeft().SetPower(-0.5);
        Motors.BackRight().SetPower(-0.5);
        Motors.BackLeft().SetPower(-0.5);
        sleep(time);

    }

    public void Shoot (double power) throws InterruptedException{
        Motors.Flipper().SetPower(power);
        sleep(850);
        Motors.Flipper().SetPower(0);
        sleep(250);
        Motors.Flipper().SetPower(.3);
        sleep(1100);
        Motors.Flipper().SetPower(0);

    }

    public void DriveStop () throws InterruptedException{
        Motors.BackLeft().SetPower(0);
        Motors.FrontLeft().SetPower(0);
        Motors.BackRight().SetPower(0);
        Motors.FrontRight().SetPower(0);
    }

    public void Collector (int time) throws InterruptedException{
        Motors.Collector().SetPower(.4);
        sleep(time);
        Motors.Collector().SetPower(0);
        sleep(2000);
    }

    public void colorSensor() throws InterruptedException {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the tiRelaveLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        float color = 0;
//        boolean redSide = false;
//        boolean blueSide = false;

        // get a reference to our ColorSensor object.
//        colorSensor = (HiTechnicNxtColorSensor)hardwareMap.colorSensor.get("color sensor");

        // turn the LED on in the beginning, just so user will know that the sensor is active.
        ColorSensor.enableLed(bLedOn);

        // wait for the start button to be pressed.


        // loop and read the RGB data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
//        while (opModeIsActive())  {

            // check the status of the x button on either gamepad.
            bCurrState = gamepad1.x;

            // check for button state transitions.
            if ((bCurrState == true) && (bCurrState != bPrevState))  {

                // button is transitioning to a pressed state.  Toggle LED.
                // on button press, enable the LED.
                bLedOn = !bLedOn;
                ColorSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(ColorSensor.red(), ColorSensor.green(), ColorSensor.blue(), hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", ColorSensor.alpha());
            telemetry.addData("Red  ", ColorSensor.red());
            telemetry.addData("Green", ColorSensor.green());
            telemetry.addData("Blue ", ColorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);
        telemetry.update();
            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            if (ColorSensor.green() > 125){
                color = 2;  //2 is red

            }
            else{
                color = 1; // 1 is blue
            }
            if  (color == 2){    //this is for blue side and testing left side
                //this is if red is the left most color
                //rotate left
//                Motors.FrontRight().SetPower(-.5);
//                Motors.FrontLeft().SetPower(-.5);
//                Motors.BackRight().SetPower(-.5);
//                Motors.BackLeft().SetPower(-.5);
//                sleep( 250);
//                Motors.FrontRight().SetPower(0);
//                Motors.FrontLeft().SetPower(0);
//                Motors.BackRight().SetPower(0);
//                Motors.BackLeft().SetPower(0);
//                sleep(2000);

                //left
//                Motors.FrontRight().SetPower(-.5);
//                Motors.FrontLeft().SetPower(.5);
//                Motors.BackRight().SetPower(-.5);
//                Motors.BackLeft().SetPower(.5);
//                sleep(150);
//                Motors.FrontRight().SetPower(0);
//                Motors.FrontLeft().SetPower(0);
//                Motors.BackRight().SetPower(0);
//                Motors.BackLeft().SetPower(0);

//                Motors.FrontRight().SetPower(.5);
//                Motors.FrontLeft().SetPower(-.5);
//                Motors.BackRight().SetPower(.5);
//                Motors.BackLeft().SetPower(-.5);
//                sleep( 2000);
//                Motors.FrontRight().SetPower(0);
//                Motors.FrontLeft().SetPower(0);
//                Motors.BackRight().SetPower(0);
//                Motors.BackLeft().SetPower(0);

                //move backward, which seems like right

                Motors.FrontRight().SetPower(.5);
                Motors.FrontLeft().SetPower(-.5);
                Motors.BackRight().SetPower(.5);
                Motors.BackLeft().SetPower(-.5);
                sleep( 700);
                Motors.FrontRight().SetPower(0);
                Motors.FrontLeft().SetPower(0);
                Motors.BackRight().SetPower(0);
                Motors.BackLeft().SetPower(0);
                sleep(3000);

                return;

            }
            if (color == 1){
                //this is if blue is the left most color
                //rotate left
//                Motors.FrontRight().SetPower(-.5);
//                Motors.FrontLeft().SetPower(-.5);
//                Motors.BackRight().SetPower(-.5);
//                Motors.BackLeft().SetPower(-.5);
//                sleep( 250);
//                Motors.FrontRight().SetPower(0);
//                Motors.FrontLeft().SetPower(0);
//                Motors.BackRight().SetPower(0);
//                Motors.BackLeft().SetPower(0);
//                sleep(2000);

                //move forward, which seems like left
                Motors.FrontRight().SetPower(-.5);
                Motors.FrontLeft().SetPower(.5);
                Motors.BackRight().SetPower(-.5);
                Motors.BackLeft().SetPower(.5);
                sleep( 700);
                Motors.FrontRight().SetPower(0);
                Motors.FrontLeft().SetPower(0);
                Motors.BackRight().SetPower(0);
                Motors.BackLeft().SetPower(0);
                sleep(3000);

                return;
            }

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }




//}
