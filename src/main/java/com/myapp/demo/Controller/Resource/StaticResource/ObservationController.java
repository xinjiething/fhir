package com.myapp.demo.Controller.Resource.StaticResource;

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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("HP")
public class ObservationController extends ResourceController implements StaticData {

    /**
     * Retrieve Observation data from FHIR server and save the information in
     * PatientModel Used to retrieve initial cholesterol level and blood pressure information of 
     * patients to allow practitioner to choose patient to monitor If there is no any observation data
     * available at this stage, practitioner is not able to monitor the patient any further
     * 
     * @param hp    Practitioner who is interested in checking his patient's
     *              information'
     * @param model Spring builtin Model to pass/ retrieve attributes
     * @return String redirection route
     */
    @RequestMapping(value = "/observations", method = RequestMethod.POST)
    public String getPatientData(@ModelAttribute("HP") PractitionerModel hp, Model model) {
        try{
            ArrayList<PatientModel> patientList = hp.getPatientList();
            for (int i = 0; i < patientList.size(); i++) {
                PatientModel patient = patientList.get(i);
                Bundle bundle = (Bundle) client.search().forResource(Observation.class)
                        .where(Observation.CODE.exactly().codes("2093-3", "55284-4"))
                        .where(Observation.PATIENT.hasId(patient.getId()))
                        .sort().descending("date").encodedJson().execute();

                for (int s=0; s < bundle.getEntry().size(); s++) {

                    Observation observation = (Observation) bundle.getEntry().get(s).getResource();
                    // blood pressure
                    if (observation.getComponent().size() > 0) {
                        for (int idx=0; idx < observation.getComponent().size(); idx++){
                            ObservationComponentComponent bpComponent = observation.getComponent().get(idx);
                            String bpCode = bpComponent.getCode().getCoding().get(0).getCode();
                            
                            double bpValue = bpComponent.getValueQuantity().getValue().doubleValue();
                            String bpUnit = bpComponent.getValueQuantity().getUnit().toString();

                            Date lastUpdated = observation.getIssued();
                            
                            patient.addBPLastUpdated(lastUpdated);
                            // diastolic blood pressure
                            if (bpCode.equals("8462-4") && patient.getDiastolicBP() == null) {
                                patient.setDiastolicBP(bpValue);
                                patient.setDiastolicBPUnit(bpUnit);
                            }
                            // systolic blood pressure
                            else if (bpCode.equals("8480-6") && patient.getSystolicBP().size() < 5){
                                patient.addSystolicBP(bpValue);
                                patient.setSystolicBPUnit(bpUnit);
                            }

                        }
                    }
                    // cholesterol
                    else {
                        if (patient.getCholesterolLevel() == null){
                            Date lastUpdated = observation.getMeta().getLastUpdated();
                            patient.setCholLastUpdated(lastUpdated);

                            double value = observation.getValueQuantity().getValue().doubleValue();
                            String unit = observation.getValueQuantity().getUnit().toString();
                            patient.setCholesterolLevel(value);
                            patient.setCholesterolUnit(unit);
                        }
                        

                    }
                            
                }

            }
            
            model.addAttribute("HP", hp);

            return "redirect:/Homepage";
        }
        catch(Exception e){
            return this.errorPageGet();
        }

    }    
    
}