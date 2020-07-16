package com.myapp.demo.Model;

public class ObservationMonitorModel {
    private boolean cholesterolMonitor = true;
    private boolean bloodPressureMonitor = true;
    


    public boolean getCholesterolMonitor() {
        return this.cholesterolMonitor;
    }

    public void setCholesterolMonitor(boolean cholesterolMonitor) {
        this.cholesterolMonitor = cholesterolMonitor;
    }

    public boolean getBloodPressureMonitor() {
        return this.bloodPressureMonitor;
    }

    public void setBloodPressureMonitor(boolean bloodPressureMonitor) {
        this.bloodPressureMonitor = bloodPressureMonitor;
    }

    public void resetSelectedMonitor(){
        this.setCholesterolMonitor(true);
        this.setBloodPressureMonitor(true);
    }

}