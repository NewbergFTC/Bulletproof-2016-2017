package us.newberg.bulletproof.modules;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.lasarobotics.vision.ftc.resq.Beacon.BeaconAnalysis;

public class Beacon 
{
    public enum Color
    {
        RED,
        BLUE,
        NONE
    };

    public static final double GOOD_CONFIDENCE = 0.60;

    private Color _leftSideColor;
    private Color _rightSideColor;

    private double _confidence;

    private boolean _beaconDetected;

    private Telemetry.Item _teleLeftSideColor;
    private Telemetry.Item _teleRightSideColor;

    private Telemetry.Item _teleConfidence;
    private Telemetry.Item _teleDetected;

    public Beacon(Telemetry tel)
    {
        _leftSideColor = Color.NONE;
        _rightSideColor = Color.NONE;
        _confidence = -1.0f;
        _beaconDetected = false;

        _teleLeftSideColor  = tel.addData("Left Color", _leftSideColor);
        _teleRightSideColor = tel.addData("Right Color", _rightSideColor);
        
        _teleConfidence = tel.addData("Confidence", _confidence);
        _teleDetected = tel.addData("Beacon Detected", _beaconDetected);
    }

    public void Update(BeaconAnalysis analysis)
    {
        _beaconDetected = analysis.isBeaconFound();
            // TODO(Garrison): Should we test to see if the left and right sides are known first?

           if (analysis.isLeftBlue())
           {
                _leftSideColor = Color.BLUE;
           }
           else
           {
                _leftSideColor = Color.RED;
           }

           if (analysis.isRightBlue())
           {
                _rightSideColor = Color.BLUE;
           }
           else
           {
                _rightSideColor = Color.RED;
           }

           _confidence = analysis.getConfidence();
    }

    public void UpdateTelemetry(Telemetry tel)
    { 
        _teleLeftSideColor  = tel.addData("Left Color", _leftSideColor);
        _teleRightSideColor = tel.addData("Right Color", _rightSideColor);
        
        _teleConfidence = tel.addData("Confidence", _confidence);
        _teleDetected = tel.addData("Beacon Detected", _beaconDetected);
    }

    public boolean IsConfident()
    {
        boolean result = _confidence >= GOOD_CONFIDENCE;

        return result;
    }

    public Color GetLeftSideColor()
    {
        return _leftSideColor;
    }

    public Color GetRightSideColor()
    {
        return _rightSideColor;
    }

    public double GetConfidence()
    {
        return _confidence;
    }

    public boolean GetBeaconDetected()
    {
        return _beaconDetected;
    }
}
