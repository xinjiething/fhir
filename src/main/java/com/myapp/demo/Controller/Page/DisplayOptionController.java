package com.myapp.demo.Controller.Page;

import com.myapp.demo.Model.DisplayOptionModel;
import com.myapp.demo.Model.ObservationMonitorModel;
import com.myapp.demo.Model.PatientModel;
import com.myapp.demo.Model.PractitionerModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "HP", "selected", "selectedButton", "Page" ,"selectedView", "selectedHighBPPatients"})
public class DisplayOptionController {
    

    
    /** 
     * This function is used to calculate the average total cholestorel and set selected view mode
     * @param selected list of selected index which is corresponding to the index of patients in 
     * @param selectedView view mode selected by user on the checkbox on UI
     * @param hp the user / health practitioner model
     * @param displayOption the model which stores the display options selected by user
     * @return String path which displays UI
     */
    @PostMapping(value = "/processSelectedPatientList")
    public String processSelectedPatientList(@ModelAttribute("selected") String[] selected, @ModelAttribute("selectedView") String[] selectedView,
            @ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        double totalChol = 0;
        int count = 0;
        displayOption.getSelectedPatientList().clear();
        for (int i = 0; i < selected.length; i++) {
            PatientModel patient = new PatientModel();
            int index = Integer.parseInt(selected[i]);
            patient = hp.getPatientList().get(index);
            displayOption.getSelectedPatientList().add(patient);
            if (!(patient.getCholesterolLevel()==null)){
                count ++;
                totalChol += patient.getCholesterolLevel();
            }
        }
        double average = totalChol / count;
        displayOption.setAverageCholesterol(average);

        ObservationMonitorModel selectedMonitor = displayOption.getSelectedMonitor();
        selectedMonitor.setCholesterolMonitor(false);
        selectedMonitor.setBloodPressureMonitor(false);
        for (int j=0; j < selectedView.length; j++){
            int index = Integer.parseInt(selectedView[j]);
            switch(index){
                case 0:
                    selectedMonitor.setCholesterolMonitor(true);
                    break;
                case 1:
                    selectedMonitor.setBloodPressureMonitor(true);
                    break;
            }
        }
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        model.addAttribute("SelectedMonitor", displayOption.getSelectedMonitor().getCholesterolMonitor());
        return "redirect:/monitor-patients";
    }

    
    /** 
     * 
     * This function is used to process the selected patient which the health practitioner wish to view with their personal details
     * @param selectedButton the button selected by user
     * @param hp the user (health practitioner model)
     * @param displayOption the model which stores the display options selected by user
     * @return String
     */
    @PostMapping(value = "/processSelectedPatient")
    public String processSelectedPatient(@ModelAttribute("selectedButton") String selectedButton, @ModelAttribute("HP") PractitionerModel hp,
            @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        String selected = selectedButton;
        int index = Integer.parseInt(selected);
        PatientModel selectedPatient = displayOption.getSelectedPatientList().get(index);
        displayOption.setSelectedPatient(selectedPatient);
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        return "redirect:/patient-details";
    }

    
    /** 
     * This function is used to clear the list of selected patient that stored inside the hp (practitioner model)
     * and reset the time for refetching new cholestorel data back to 60 seconds.
     * @param hp
     * @param model
     * @return String
     */
    @PostMapping(value = "/processClearSelectedPatient")
    public String processClearSelectedPatient(@ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        displayOption.resetSelectedPatients();
        displayOption.setviewRefreshTime(60);
        model.addAttribute("Page", displayOption);
        return "redirect:/Homepage";
    }


    
    /** 
     * Saving  the threshold values for high systolic and diastolic bloop pressure entered by user
     * @param inputSystolicBP threshold value for high systolic blood pressure entered by user
     * @param inputDiastolicBP threshold value for high diastolic blood pressure entered by user
     * @param displayOption the model which stores the display options selected by user
     * @return String
     */
    @GetMapping(value = "/bloodpressure")
    public String processInputBloodPressure(@RequestParam(value = "inputSystolicBP") int inputSystolicBP, 
        @RequestParam(value = "inputDiastolicBP") int inputDiastolicBP, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model){
            displayOption.setHighSystolicBP(inputSystolicBP);
            displayOption.setHighDiastolicBP(inputDiastolicBP);
            model.addAttribute("Page", displayOption);
            return "redirect:/monitor-patients";
    }

    
    /** 
     * Save list of patients which user wish to monitor their latest 5 histories
     * @param selected the list of patients with high blood pressure which the user wish to monitor their latest 5 histories
     * @param hp the user (health practitioner model)
     * @param displayOption the model which stores the display options selected by user
     * @return String
     */
    @PostMapping(value = "/processSelectedHighBP")
    public String processSelectedHighBP(@ModelAttribute("selectedHighBPPatients") String[] selected,
            @ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {
        for (int i = 0; i < selected.length; i++) {
            int index = Integer.parseInt(selected[i]);
            PatientModel patient = displayOption.getSelectedPatientList().get(index);
            displayOption.getSelectedHighBPPatients().add(patient);
        }
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        return "redirect:/high-systolic-bp-monitor";
    }

}