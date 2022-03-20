package sample;

import javafx.scene.control.Label;

import java.util.TimerTask;

public class MyTimer extends TimerTask {
    Label timerL;
    MyTimer(Label timerL){
        this.timerL = timerL;
    }

    @Override
    public void run() {

    }
}
