package com.myapp.demo.Controller.Resource.StaticResource;

import java.util.Date;
import java.util.List;

import com.myapp.demo.Controller.Resource.ResourceController;
import com.myapp.demo.Model.PatientModel;
import com.myapp.demo.Model.PractitionerModel;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.uhn.fhir.rest.api.SearchTotalModeEnum;

@Controller
@SessionAttributes("HP")
public class EncounterController extends ResourceController implements StaticData{


    
    /** 
     * Access server and retrieve data from Encounter module in FHIR server
     * @param hp the subject that we are interested in getting his patient information
     * @param model spring framework inbuilt UI Model to pass/retrieve attributes
     * @return String redirection path
     */
    @RequestMapping(value = "/encounter", method = RequestMethod.POST)
    public String getPatientData(@ModelAttribute("HP") PractitionerModel hp, Model model) {
        try{
            ctx.getRestfulClientFactory().setSocketTimeout(150*1000);
        
        
            int resultPerPage = 50;
            Bundle res = client.search().forResource(Encounter.class)
                        .where(Encounter.PARTICIPANT.hasChainedProperty("Practitioner", Practitioner.IDENTIFIER.exactly().systemAndValues("http://hl7.org/fhir/sid/us-npi", hp.getIdentifier())))
                        .include(Encounter.INCLUDE_PATIENT)
                        .include(Encounter.INCLUDE_PARTICIPANT)
                        .count(resultPerPage)
                        .totalMode(SearchTotalModeEnum.ACCURATE)
                        .encodedJson().returnBundle(Bundle.class).execute();

            int totalEncounter = res.getTotal();
            if (totalEncounter == 0){
                return "redirect:/loginError"; 

            }
            Bundle bundle = res;
            int entryCount;
            int offset = 0;
            int includeStart;

            while(offset < totalEncounter){
                if (totalEncounter - offset >= resultPerPage){
                    includeStart = resultPerPage;
                }
                else{
                    includeStart = totalEncounter - offset;
                }
                List<BundleEntryComponent> entryList = bundle.getEntry();
                entryCount = entryList.size();
                for (int i = includeStart; i < entryCount; i++) {
                    Resource entry = entryList.get(i).getResource();
                    // get practitioner's information
                    if (entry.getResourceType() == ResourceType.Practitioner){
                        Practitioner p = (Practitioner) entry;
                        String family = p.getName().get(0).getFamily();
                        String given = "";
                        for (StringType names : p.getName().get(0).getGiven()){
                            String name = names.asStringValue();
                            given += name + " ";
                        }
                        String pre = "";
                        for (StringType prefixes : p.getName().get(0).getPrefix()){
                            String prefix = prefixes.asStringValue();
                            pre += prefix + " ";
                        }
                        hp.setPrefix(pre);
                        hp.setFamilyName(family);
                        hp.setGivenName(given);
                    }
                    // store Patient information in PatientModel
                    else if(entry.getResourceType() == ResourceType.Patient){
                        boolean existed = false;
                        Patient patient = (Patient) entry;
                        // check if patient exists
                        for (PatientModel pat : hp.getPatientList()){
                            String patientId = patient.getIdElement().getIdPart();
                            if (pat.getId().equals(patientId)){
                                existed = true;
                                continue;
                            }
                        }
                        // save patient's information in PatientModel if patient not exist in practitioner's list
                        if (existed == false){
                            this.savePatientInfo(patient, hp);
                        }
                    }
                }
                offset += includeStart;

                // load next page if next page is available
                if (bundle.getLink(Bundle.LINK_NEXT) != null) {
                    bundle = client.loadPage().next(bundle).execute();
                }
                

            }
            
            model.addAttribute("HP", hp);
            return "forward:/observations"; 
        }
        catch(Exception e){
            return this.errorPageGet();
        }
        
    }

    
    /** 
     * Save patient's information in PatientModel
     * @param p Patient retrieve from server
     * @param hp Practitioner who is interested in checking his patient's information'
     */
    public void savePatientInfo(Patient p, PractitionerModel hp){
        PatientModel patient = new PatientModel();
        patient.setId(p.getIdElement().getIdPart());
        
        String family = p.getName().get(0).getFamily();
        patient.setFamilyName(family);
        
        String given = "";
        for (StringType names : p.getName().get(0).getGiven()){
            given = String.join(" ", names.asStringValue());

        }
        patient.setGivenName(given);

        Date dob = p.getBirthDate();
        patient.setBirthDate(dob);

        String gender = p.getGender().toString();
        patient.setGender(gender);

        if (p.getAddress().get(0).hasLine()){
            String addrLine = "";
            for (StringType lines : p.getAddress().get(0).getLine()){
                addrLine = String.join(" ", lines.asStringValue());
            }
            patient.setAddrLine(addrLine);
        }

        if (p.getAddress().get(0).hasCity()){
            patient.setCity(p.getAddress().get(0).getCity());
        }

        if (p.getAddress().get(0).hasPostalCode()){
            patient.setPoscode(p.getAddress().get(0).getPostalCode());
        }

        if (p.getAddress().get(0).hasState()){
            patient.setState(p.getAddress().get(0).getState());
        }
        
        if (p.getAddress().get(0).hasCountry()){
            patient.setCountry(p.getAddress().get(0).getCountry());
        }

        // add patient to practitioner's patient list
        hp.addPatientToList(patient);
    }


    
}