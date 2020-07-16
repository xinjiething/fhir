package com.myapp.demo.Controller.Page;

import com.myapp.demo.Model.DisplayOptionModel;
import com.myapp.demo.Model.PractitionerModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "HP", "selectedButton", "Page" })
public class PatientDetailsController implements PageController<String> {
    /**
     * This function is used to process the selected patient that is selected for
     * viewing the personal details of the patient
     * 
     * @param hp    the user (HealthPractitioner model)
     * @param model
     * @return String
     */
    @PostMapping(value = "/patient-details")
    public String processRequest(String request1,@RequestParam(value = "selectedButton") String selectedButton, 
            @ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        model.addAttribute("selectedButton", selectedButton);
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        return "forward:/processSelectedPatient";
    }

    /**
     * 
     * This function is used to render patientDetails page
     * 
     * @param hp    the current user
     * @param model
     * @return String
     */
    @RequestMapping(value = "/patient-details", method = RequestMethod.GET)
    public String displayView(@ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        model.addAttribute("HP", hp);
        model.addAttribute("Patient", displayOption.getSelectedPatient());
        model.addAttribute("Page", displayOption);
        return "PatientDetails";
    }

}
