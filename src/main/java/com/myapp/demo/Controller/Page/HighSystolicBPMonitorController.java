package com.myapp.demo.Controller.Page;

import com.google.gson.Gson;
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
@SessionAttributes({ "HP", "Page","selectedHighBPPatients","selectedButton"})
public class HighSystolicBPMonitorController implements PageController<String[]> {


    
    /** 
     * Receive requesst from user interactions, decide which selected button corresponds to which page to be displayed
     * @param chooseHighBPPatient list of high blood pressure patients selected by user
     * @param selectedButton type of selected button (view details or monitor)
     * @param displayOption the model which stores the display options selected by user
     * @return String
     */
    @PostMapping(value = "/high-systolic-bp-monitor")
    public String processRequest(@RequestParam(value = "chooseHighBPPatient",required=false) String[] chosenHighBPPatient, 
            @RequestParam(value = "selectedButton") String[] selectedButton, @ModelAttribute("HP") PractitionerModel hp,
            @ModelAttribute("Page")DisplayOptionModel displayOption, Model model) {
        model.addAttribute("HP", hp);
        model.addAttribute("Page", displayOption);
        int button = Integer.parseInt(selectedButton[0]);
        if (button > -1){
            model.addAttribute("selectedButton", button);
            return "forward:/processSelectedPatient";
        }  
        else{
            model.addAttribute("selectedHighBPPatients", chosenHighBPPatient);
            return "forward:/processSelectedHighBP";
        }  
    }

    
    /** 
     * Pass neccessary session attributes to UI
     * @param hp the user (health practitioner model)
     * @param displayOption the model which stores the display options selected by user
     * @param model
     * @return String
     */
    @RequestMapping(value = "/high-systolic-bp-monitor", method = RequestMethod.GET)
    public String displayView(@ModelAttribute("HP") PractitionerModel hp, @ModelAttribute("Page") DisplayOptionModel displayOption, Model model) {     
        model.addAttribute("HP",hp);
        model.addAttribute("Page", displayOption);
        model.addAttribute("selectedPatient", new Gson().toJson(displayOption.getSelectedHighBPPatients()));
        return "HighSystolicBPMonitor";
    }

    
}