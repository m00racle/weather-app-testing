package com.mooracle.service.dto.weather;

import com.mooracle.service.dto.Dto;

import java.util.List;

/** Entry 13: Creating ForecastData class service
 *  1.  This service is to make some kind of model how a data from Dark Sky API will looks like to be presented
 *  2.  The form of this class is more like a model rather than a business logic code
 *  3.  The content only consist of field declarations then getters and setters with no constructors
 *  4.  Looks like this class is being prepared to fetch any Forecast data and turns it into POJO
 *  5.  We will see the benefit in extending Dto abstract class when using Condition field we don't have to import it
 *  6.  As this will shown and process all weather condition data it needs to incorporate List of Conditions
 *  7.  By the look on how the Condition class is made the data will be time function.
 *  */

public class ForecastData extends Dto {
    //field declarations:
    private String summary;
    private String icon;
    private List<Condition> data;

    //getters and setters

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Condition> getData() {
        return data;
    }

    public void setData(List<Condition> data) {
        this.data = data;
    }
}
