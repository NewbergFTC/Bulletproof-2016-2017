package us.newberg.bulletproof.modules;

import com.elvishew.xlog.XLog;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class LightSensor
{
    public static final float DEPLOY_POS = 1.0f;
    public static final float CLOSE_POS  = 0.0f;

    private HiTechnicNxtLightSensor _lightSensor;
    private Servo _servo;
    private double _initalValue;

    private boolean _enableLogging;

    public LightSensor(HiTechnicNxtLightSensor lightSensor, Servo servo)
    {
        this(lightSensor, servo, false);
    }

    public LightSensor(HiTechnicNxtLightSensor lightSensor, Servo servo, boolean enableLogging)
    {
        _lightSensor = lightSensor;
        _servo = servo;
        _enableLogging = enableLogging;
        _initalValue = -1;

        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Construction Complete");
        }
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

        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Calibrate complete with light value " + String.valueOf(_initalValue));
        }
    }

    public void Deploy()
    {
        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Deploy Sensor");
        }

//        _servo.setPosition(DEPLOY_POS);
    }

    public void Close()
    {
        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Close Sensor");
        }
//        _servo.setPosition(CLOSE_POS);
    }

    public void EnableLED()
    {
        if (_enableLogging)
        {
//            XLog.tag("LightSensor").d("");
            XLog.tag("LightSensor").d("Enable LED");
        }

        _lightSensor.enableLed(true);
    }

    public void DisableLED()
    {
        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Disable LED");
        }

        _lightSensor.enableLed(false);
    }

    public double GetLight()
    {
        return _lightSensor.getLightDetected();
    }

    public double GetLightRaw()
    {
        return _lightSensor.getRawLightDetected();
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

        if (_enableLogging)
        {
            XLog.tag("LightSensor").d("Line search with result " + (result ? "found" : "not found"));
        }

        return result;
    }
}
