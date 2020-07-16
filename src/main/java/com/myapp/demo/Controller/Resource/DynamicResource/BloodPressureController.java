package com.myapp.demo.Controller.Resource.DynamicResource;

import java.util.ArrayList;
import java.util.Date;

import com.myapp.demo.Controller.Resource.ResourceController;
import com.myapp.demo.Model.PatientModel;
import com.myapp.demo.Model.PractitionerModel;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationComponentComponent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("HP")
public class BloodPressureController extends ResourceController implements DynamicData{
    
    /** 
     * Retrieve latest blood pressure data from server and check if the data in server is updated.
     * @param hp 
     * @param model
     * @return boolean true if the blood pressure data is updated, false otherwise
     */
    @RequestMapping(value = "/update-blood-pressure", method = RequestMethod.POST)
    @ResponseBody
    public boolean getUpdatedData(@ModelAttribute("HP") PractitionerModel hp, Model model) {
        boolean updated = false;
        ArrayList<PatientModel> patientList = hp.getSelectedPatientList();

        // search for all selected patient's blood  in server
        for (int i = 0; i < patientList.size(); i++) {
            PatientModel patient = patientList.get(i);
            Bundle bundle = (Bundle) client.search().forResource(Observation.class)
                    .where(Observation.CODE.exactly().code("55284-4")).where(Observation.PATIENT.hasId(patient.getId()))
                    .sort().descending("date").encodedJson().execute();

            if (bundle.getEntry().size() > 0) {
                for (int j=0; j<bundle.getEntry().size() && j < 5; j++) {
                    Observation observation = (Observation) bundle.getEntry().get(j).getResource();

                    // update patient's blood pressure if the value is updated (i.e. lastUpdated
                    // is later than patient's lastUpdated in PatientModel)
                    if (observation.getMeta().getLastUpdated().after(patient.getBPLastUpdated().get(0))) {
                        updated = false;
                        Date lastUpdated = observation.getMeta().getLastUpdated();
                        patient.addBPLastUpdated(lastUpdated);

                        for (int idx=0; idx < observation.getComponent().size(); idx++){
                            ObservationComponentComponent bpComponent = observation.getComponent().get(idx);
                            String bpCode = bpComponent.getCode().getCoding().get(0).getCode();
                            
                            double bpValue = bpComponent.getValueQuantity().getValue().doubleValue();
                            String bpUnit = bpComponent.getValueQuantity().getUnit().toString();
                            
                            // diastolic blood pressure
                            if (bpCode.equals("8462-4")) {
                                patient.getBPLastUpdated().set(j, lastUpdated);
                                patient.setDiastolicBP(bpValue);
                                patient.setDiastolicBPUnit(bpUnit);
                            }
                            // systolic bloop pressure
                            else if (bpCode.equals("8480-6")){
                                patient.getBPLastUpdated().set(j, lastUpdated);
                                patient.getSystolicBP().set(j, bpValue);
                                patient.setSystolicBPUnit(bpUnit);
                            }
                        }
                    }
                }

            }
        }
        model.addAttribute("HP", hp);
        return updated;
        
    }




}