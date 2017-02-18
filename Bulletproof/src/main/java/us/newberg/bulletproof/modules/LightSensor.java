package us.newberg.bulletproof.modules;

import com.elvishew.xlog.XLog;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class LightSensor
{
    public static final String TAG = "LightSensor";

    public static final float DEPLOY_POS = 1.0f;
    public static final float CLOSE_POS  = 0.0f;

    private HiTechnicNxtLightSensor _lightSensor;
    private Servo _servo;
    private double _initalValue;

    public LightSensor(HiTechnicNxtLightSensor lightSensor, Servo servo)
    {
        _lightSensor = lightSensor;
        _servo = servo;
        _initalValue = -1;

        XLog.tag(TAG).d("Construction Complete");
    }

    public void Calibrate()
    {
        EnableLED();
        Deploy();

        try
        {
            Thread.sleep(250);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        _initalValue = GetLight();

        Close();

        XLog.tag(TAG).d("Calibrate complete with light value " + String.valueOf(_initalValue));
    }

    public void Deploy()
    {
        XLog.tag(TAG).d("Deploy Sensor");

//        _servo.setPosition(DEPLOY_POS);
    }

    public void Close()
    {
        XLog.tag(TAG).d("Close Sensor");

//        _servo.setPosition(CLOSE_POS);
    }

    public void EnableLED()
    {

//            XLog.tag("LightSensor").d("");
        XLog.tag(TAG).d("Enable LED");

        _lightSensor.enableLed(true);
    }

    public void DisableLED()
    {
        XLog.tag(TAG).d("Disable LED");

        _lightSensor.enableLed(false);
    }

    public double GetLight()
    {
        double result = _lightSensor.getLightDetected();

        XLog.tag(TAG).d("Got Value " + String.valueOf(result));

        return result;
    }

    public double GetLightRaw()
    {
        double result = _lightSensor.getRawLightDetected();

        XLog.tag(TAG).d("Got Raw Value " + String.valueOf(result));

        return result;
    }

    public boolean FoundLine()
    {
        boolean result = false;

        double currentLight = GetLight();

        // TODO(Garrison): Measure a real value for here
        if (currentLight > _initalValue + 0.03f)
        {
            result = true;
        }

        XLog.tag(TAG).d("Line search with result " + (result ? "found" : "not found"));

        return result;
    }
}
