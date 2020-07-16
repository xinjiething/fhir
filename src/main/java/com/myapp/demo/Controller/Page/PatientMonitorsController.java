package com.myapp.demo.Controller.Page;
import com.google.gson.Gson;
import com.myapp.demo.Model.DisplayOptionModel;
import com.myapp.demo.Model.PatientModel;
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
@SessionAttributes({ "HP", "selected", "Page","selectedView" })
public class PatientMonitorsController implements PageController<String[]> {

    /**
     * @param selected the selected patient from the Homepage
     * @param hp
     * @param model
     * @return String
     */
    @PostMapping(value = "/monitor-patients")
    public String processRequest(@RequestParam(value = "inputPatient") String[] selected,@RequestParam(value="selectedView") String[] selectedView,
            @ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page")DisplayOptionModel displayOption ,Model model) {
        model.addAttribute("selected", selected);
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        model.addAttribute("selectedView", selectedView);
        return "forward:/processSelectedPatientList";
    }

    /**
     * This function is used to render the selected patient with their corresponding
     * cholesterol level and/or blood pressure to the view.
     * Based on the selected monitor (cholesterol and/or blood pressure), it will redirect to corresponding view.
     * 
     * @param hp    the current user(Health Practitioner)
     * @param model
     * @return String
     */
    @RequestMapping(value = "/monitor-patients", method = RequestMethod.GET)
    public String displayView(@ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        int selected[] = new int[displayOption.getSelectedPatientList().size()];
        if (displayOption.getSelectedHighBPPatients().size() > 0) {
            for (int i = 0; i < displayOption.getSelectedHighBPPatients().size(); i++) {
                PatientModel currentPatient = displayOption.getSelectedHighBPPatients().get(i);
                int index = displayOption.getSelectedPatientList().indexOf(currentPatient);
                selected[index] = 1;
            }
        }
        model.addAttribute("chosenPatient", selected);
        model.addAttribute("HP",hp);
        model.addAttribute("selectedPatient", new Gson().toJson(displayOption.getSelectedPatientList()));
        model.addAttribute("Page", displayOption);
        boolean cholesterolView = displayOption.getSelectedMonitor().getCholesterolMonitor();
        boolean bloodPressureView = displayOption.getSelectedMonitor().getBloodPressureMonitor();
        if (cholesterolView == true && bloodPressureView == false){
            return "PatientsCholesterolMonitor";
        }
        else if (cholesterolView == false && bloodPressureView == true){
            return "PatientsBloodPressureMonitor";
        }
        else{
            return "PatientsBloodPressureAndCholesterolMonitor";
        }
      
    }

    /**
     * 
     * This function is invoked when the user click on the "Stop monitoring" button
     * and it will forward to processClearSelected function to clear the currently
     * selected patient list
     * 
     * @param hp    the current user(Health Practitioner)
     * @param model
     * @return String
     */
    @PostMapping("/clearSelected")
    public String clearSelected(@ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        return "forward:/processClearSelectedPatient";
    }



}
