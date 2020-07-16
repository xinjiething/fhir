package com.myapp.demo.Model;

import java.util.ArrayList;
import java.util.Date;

public class PatientModel {
    /* 
    This model is used to store the details of a patient 
    */
    private String id;
    private String familyName;
    private String givenName;
    private Date birthDate;
    private String gender;
    private String addrLine = "N/A";
    private String city = "N/A";
    private String poscode = "N/A";
    private String state ="N/A";
    private String country = "N/A";
    private Date cholLastUpdated;
    private Double cholesterolLevel;
    private String cholesterolUnit;
    private ArrayList<Date> bpLastUpdated = new ArrayList<Date>();
    private ArrayList<Double> systolicBP = new ArrayList<Double>();
    private String systolicBPUnit;
    private Double diastolicBP;
    private String diastolicBPUnit;


    public PatientModel() {};

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFamilyName(){
        return this.familyName;
    }

    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getAddrLine() {
        return this.addrLine;
    }

    public void setAddrLine(String addrLine){
        this.addrLine = addrLine;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getPoscode() {
        return this.poscode;
    }

    public void setPoscode(String poscode){
        this.poscode = poscode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public Double getCholesterolLevel() {
        return this.cholesterolLevel;
    }

    public void setCholesterolLevel(Double cholesterolLevel){
        this.cholesterolLevel = cholesterolLevel;
    }

    public String getCholesterolUnit() {
        return this.cholesterolUnit;
    }

    public void setCholesterolUnit(String unit){
        this.cholesterolUnit = unit;
    }

    public Date getCholLastUpdated() {
        return this.cholLastUpdated;
    }

    public void setCholLastUpdated(Date lastUpdated){
        this.cholLastUpdated = lastUpdated;
    }

    public ArrayList<Date> getBPLastUpdated() {
        return this.bpLastUpdated;
    }

    public void addBPLastUpdated(Date lastUpdated){
        this.bpLastUpdated.add(lastUpdated);
    }

    public void addSystolicBP(Double systolicBP){
        this.systolicBP.add(systolicBP);
    }

    public ArrayList<Double> getSystolicBP() {
        return this.systolicBP;
    }

    public void setSystolicBPUnit(String unit){
        this.systolicBPUnit = unit;
    }

    public String getSystolicBPUnit() {
        return this.systolicBPUnit;
    }

    public void setDiastolicBP(Double diastolicBP){
        this.diastolicBP = diastolicBP;
    }

    public Double getDiastolicBP() {
        return this.diastolicBP;
    }

    public void setDiastolicBPUnit(String diastolicBPUnit){
        this.diastolicBPUnit = diastolicBPUnit;
    }

    public String getDiastolicBPUnit() {
        return this.diastolicBPUnit;
    }

    
}