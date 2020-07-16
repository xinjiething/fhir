package com.myapp.demo.Controller.Resource.DynamicResource;

import com.myapp.demo.Model.PractitionerModel;


import org.springframework.ui.Model;

/**
 * Used by Controllers that need to access server at interval to retrieve latest updated data
 */
public interface DynamicData {
    boolean getUpdatedData(PractitionerModel hp, Model model);
}