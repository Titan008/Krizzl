package com.example.scribbl.Paint;

import android.graphics.Path;

public class FingerPath {

    public int color;
    public boolean small;
    public boolean big;
    public int strokeWidth;
    public Path path;


    public FingerPath(int color, boolean small, boolean big, int strokeWidth, Path path) {
        this.color = color;
        this.small = small;
        this.big = big;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
