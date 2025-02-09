package org.firstinspires.ftc.teamcode.resource;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class PipeLineTest extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}