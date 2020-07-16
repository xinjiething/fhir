package com.myapp.demo.Controller.Resource.DynamicResource;

import java.util.ArrayList;
import java.util.Date;

import com.myapp.demo.Controller.Resource.ResourceController;
import com.myapp.demo.Model.PatientModel;
import com.myapp.demo.Model.PractitionerModel;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("HP")
@Controller
public class CholesterolController extends ResourceController implements DynamicData {

    /**
     * Retrieve patients' cholesterol level from FHIR server at
     * interval (invoked by jQuery in populatePatient.jsp at interval)
     * 
     * @param hp    Practitioner who is interested in checking his patient's
     *              information'
     * @param model Spring builtin Model to pass/ retrieve attributes
     * @return boolean true if patient's Observation information is updated and
     *         false otherwise
     */
    // @Override
    @RequestMapping(value = "/update-cholesterol", method = RequestMethod.POST)
    @ResponseBody
    public boolean getUpdatedData(@ModelAttribute("HP") PractitionerModel hp, Model model) {
        boolean updated = false;
        ArrayList<PatientModel> patientList = hp.getSelectedPatientList();

        // search for all selected patient's Observation in server
        for (int i = 0; i < patientList.size(); i++) {
            PatientModel patient = patientList.get(i);
            Bundle bundle = (Bundle) client.search().forResource(Observation.class)
                    .where(Observation.CODE.exactly().code("2093-3")).where(Observation.PATIENT.hasId(patient.getId()))
                    .sort().descending("date").encodedJson().execute();

            if (bundle.getEntry().size() > 0) {
                Observation observation = (Observation) bundle.getEntry().get(0).getResource();

                // update patient's cholesterol level if the value is updated (i.e. lastUpdated
                // is later than patient's lastUpdated in PatientModel)
                if (observation.getMeta().getLastUpdated().after(patient.getCholLastUpdated())) {
                    updated = true;
                    Date lastUpdated = observation.getMeta().getLastUpdated();
                    patient.setCholLastUpdated(lastUpdated);

                    double value = observation.getValueQuantity().getValue().doubleValue();
                    String unit = observation.getValueQuantity().getUnit().toString();
                    patient.setCholesterolLevel(value);
                    patient.setCholesterolUnit(unit);
                }

            }
        }
        model.addAttribute("HP", hp);
        return updated;

    }

    
}