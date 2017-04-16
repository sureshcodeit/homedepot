package com.homedepot.components.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.homedepot.constants.Constants;


@Model(adaptables = Resource.class)
/**
 * Sling Model class to get the currency types authored in Currency Converter Component
 * 
 */
public class CurrencyConverterModel implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterModel.class);
	private final List<String> currencyTypesList = new ArrayList<>();
	
	@Self
    private Resource resource;
	
    @PostConstruct
    protected void init() {
    	if(null != resource){
    		ValueMap map = resource.getValueMap();
    		String[] currencyTypes = map.get(CURRENCY_TYPES, String[].class);
    		if(currencyTypes != null && currencyTypes.length > 0){
    			try {
	    			logger.debug("Currency Types length: {}", currencyTypes.length);
	    			for(String type : currencyTypes){
	    				JSONObject jsonObj = new JSONObject(type);
	    				if(StringUtils.isNotEmpty(jsonObj.getString(CURRENCY_TYPE))){
	    					currencyTypesList.add(jsonObj.getString(CURRENCY_TYPE));
	    				}	    				
	    			}
	    		} catch (JSONException jsonException) {
					logger.error("[Exception while initializing the CurrencyConverterModel : ] {}", jsonException);
				}
    		}
    	}
 	}

	public List<String> getCurrencyTypesList() {
		return currencyTypesList;
	}

	public boolean isCurrencyTypesEmpty() {
		return currencyTypesList.size() < 0 ? true : false;
	}
}