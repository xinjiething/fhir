package com.myapp.demo.Model;

import java.util.ArrayList;
/*
This model is used to store health practitioner details when using the system
*/

public class PractitionerModel {
    private String identifier;
    private String prefix;
    private String familyName;
    private String givenName;
    private ArrayList<String> idList = new ArrayList<String>();
    private ArrayList<String> patientIdList = new ArrayList<String>();
    private ArrayList<PatientModel> patientList = new ArrayList<PatientModel>();
    private ArrayList<PatientModel> selectedPatientList = new ArrayList<PatientModel>();
    private PatientModel selectedPatient;

    public PractitionerModel(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public ArrayList<String> getIdList() {
        return this.idList;
    }

    public void addIdToList(String id) {
        this.idList.add(id);
    }

    public ArrayList<String> getPatientIdList() {
        return this.patientIdList;
    }

    public void addPatientIdToList(String patientId) {
        this.patientIdList.add(patientId);
    }

    public ArrayList<PatientModel> getPatientList() {
        return this.patientList;
    }

    public void addPatientToList(PatientModel patient) {
        this.patientList.add(patient);
    }

    public void setSelectedPatientList(ArrayList<PatientModel> patient) {
        this.selectedPatientList = patient;
    }

    public ArrayList<PatientModel> getSelectedPatientList() {
        return this.selectedPatientList;
    }

    public void setSelectedPatient(PatientModel patient) {
        this.selectedPatient = patient;
    }

    public PatientModel getSelectedPatient() {
        return this.selectedPatient;
    }


}