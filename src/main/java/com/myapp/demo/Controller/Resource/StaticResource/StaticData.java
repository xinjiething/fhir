package com.myapp.demo.Controller.Resource.StaticResource;

import com.myapp.demo.Model.PractitionerModel;

import org.springframework.ui.Model;

/**
 * Used by Controllers that need to fetch static data (e.g. name, id etc that do not need to be updated more than once/ frequently) from FHIR server
 */
public interface StaticData {
    String getPatientData(PractitionerModel hp, Model model);
}