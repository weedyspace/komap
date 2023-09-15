package com.my.diseaseControl.vo;

import java.math.BigDecimal;

public class AreaGeojson {
	private int GeojsonIndex = 0;
	private String GeojsonUrl = null;
	private BigDecimal AreaMeasure = null;
	
	
	public int getGeojsonIndex() {
		return GeojsonIndex;
	}
	public void setGeojsonIndex(int geojsonIndex) {
		GeojsonIndex = geojsonIndex;
	}
	public String getGeojsonUrl() {
		return GeojsonUrl;
	}
	public void setGeojsonUrl(String geojsonUrl) {
		GeojsonUrl = geojsonUrl;
	}
	public BigDecimal getAreaMeasure() {
		return AreaMeasure;
	}
	public void setAreaMeasure(BigDecimal areaMeasure) {
		AreaMeasure = areaMeasure;
	}


	
}