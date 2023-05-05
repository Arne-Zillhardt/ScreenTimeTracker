package Backend;

import GUI.GUI;
import GUI.Pages;
import javafx.application.Platform;

public class Clock extends Thread {
    double startTime;
    double time;
    double sec;
    double setSec;
    double min;
    double setMin;
    double hou;
    double SetHou;
    Pages page;

    public synchronized void start(Pages page, double h, double m, double s) {
        //set time
        startTime = System.currentTimeMillis();
        this.page = page;
        SetHou = hou = h;
        setMin = min = m;
        setSec = sec = s;

        System.out.println(setMin);
        super.start();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            sec = ((System.currentTimeMillis() - startTime) / 1000) + setSec;
            if (sec >= 60) {
                sec = setSec = 0;
                min += 1;
                startTime = System.currentTimeMillis();
            }
            if (min >= 60) {
                hou += 1;
                min = 0;
            }

            time = min + (sec/60) + (hou * 60);
            System.out.println(time);

            Platform.runLater(() -> page.setTime(hou, min, sec, time));
        }
    }

    @Override
    public void interrupt() {
        //Upload time
        super.interrupt();
    }
}
