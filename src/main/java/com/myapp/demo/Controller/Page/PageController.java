package com.myapp.demo.Controller.Page;

import com.myapp.demo.Model.DisplayOptionModel;
import com.myapp.demo.Model.PractitionerModel;
import org.springframework.ui.Model;

public interface PageController<T> {
    /*
     * This interface is used by the page controller that render and redirect the
     * view
     */
    String processRequest(T firstRequest, T secondRequest, PractitionerModel hp, DisplayOptionModel displayOption, Model model);

    String displayView(PractitionerModel hp, DisplayOptionModel displayOption, Model model);
}