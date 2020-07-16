package com.myapp.demo.Model;

import java.util.ArrayList;
/*
This model is used to store page viewing details/ options (i.e. the control of )
*/

public class DisplayOptionModel {

    private ArrayList<PatientModel> selectedPatientList = new ArrayList<PatientModel>();
    private PatientModel selectedPatient;
    private Integer viewRefreshTime = 60;
    private Double averageCholesterol;
    private Integer highSystolicBP = 130;
    private Integer highDiastolicBP = 80;
    private ArrayList<PatientModel> selectedHighBPPatients = new ArrayList<PatientModel>();
    // private String[] selectedView =new String[]{"1","1"};
    private ObservationMonitorModel selectedMonitor = new ObservationMonitorModel();


    public ArrayList<PatientModel> getSelectedPatientList() {
        return this.selectedPatientList;
    }

    public void setSelectedPatient(PatientModel patient) {
        this.selectedPatient = patient;
    }

    public PatientModel getSelectedPatient() {
        return this.selectedPatient;
    }

    public void setviewRefreshTime(Integer time) {
        this.viewRefreshTime = time;
    }

    public Integer getviewRefreshTime() {
        return this.viewRefreshTime;
    }

    public Double getAverageCholesterol() {
        return this.averageCholesterol;
    }

    public void setAverageCholesterol(Double averageChol) {
        this.averageCholesterol = averageChol;
    }

    public Integer getHighSystolicBP(){
        return this.highSystolicBP;
    }

    public void setHighSystolicBP(Integer highSystolicBP){
        this.highSystolicBP = highSystolicBP;
    }

    public Integer getHighDiastolicBP(){
        return this.highDiastolicBP;
    }

    public void setHighDiastolicBP(Integer highDiastolicBP){
        this.highDiastolicBP = highDiastolicBP;
    }

    // public void setSelectedView(String[] selectedView){
    //     this.selectedView = selectedView;
    // }

    // public String[] getSelectedView(){
    //     return this.selectedView;
    // }

    public ArrayList<PatientModel> getSelectedHighBPPatients(){
        return this.selectedHighBPPatients;
    }

    public void addSelectedHighBPPatients(PatientModel selectedPatient){
        this.selectedHighBPPatients.add(selectedPatient);
    }

    public ObservationMonitorModel getSelectedMonitor() {
        return this.selectedMonitor;
    }
    
    public void setSelectedMonitor(ObservationMonitorModel observationMonitor) {
        this.selectedMonitor = observationMonitor;
    }

    public void resetSelectedPatients(){
        this.selectedPatientList.clear();
    }

    public void resetSelectedHighBPPatients(){
        this.selectedHighBPPatients.clear();
    }
    
}