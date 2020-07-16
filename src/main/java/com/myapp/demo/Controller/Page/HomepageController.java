package com.myapp.demo.Controller.Page;

import com.myapp.demo.Model.PractitionerModel;

import com.myapp.demo.Model.DisplayOptionModel;
import com.myapp.demo.Model.PatientModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"HP", "Page"})
public class HomepageController implements PageController<String> {

    /**
     * This function is used function is used to forward to the encounter controller
     * for getting the information of the encounter
     * 
     * @param identifier : The identifier key in by the health practitioner
     * @param p:         The health practitioner model
     * @param model
     * @return String
     */
    @RequestMapping(value = "/Homepage", method = RequestMethod.POST)
    public String processRequest(String request1, @ModelAttribute("identifier") String identifier, PractitionerModel p, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        PractitionerModel hp = new PractitionerModel(identifier);
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        return "forward:/encounter";
    }

    /**
     * This function will first check if there is any selected patient previously so
     * that the checkbox can show with the view and render the view of the patient
     * to be selected. 
     * It will also clear the previously selected high systolic blood patients
     * 
     * @param hp
     * @param model
     * @return String
     */
    @RequestMapping(value = "/Homepage", method = RequestMethod.GET)
    public String displayView(@ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        displayOption.resetSelectedHighBPPatients();
        int selected[] = new int[hp.getPatientList().size()];
        if (displayOption.getSelectedPatientList().size() > 0) {
            for (int i = 0; i < displayOption.getSelectedPatientList().size(); i++) {
                PatientModel currentPatient = displayOption.getSelectedPatientList().get(i);
                int index = hp.getPatientList().indexOf(currentPatient);
                selected[index] = 1;
            }
        }
        model.addAttribute("HP", hp);
        model.addAttribute("selected", selected);
        model.addAttribute("Page", displayOption);
        return "Homepage";
    }

}