package com.steven.ws.dto;

import java.util.List;

public class ZipCodeRangeDTO {
	private List<String> zipRange;

	public List<String> getZipRange() {
		return zipRange;
	}

	public void setZipRange(List<String> zipRange) {
		this.zipRange = zipRange;
	}

	@Override
	public String toString() {
		return "ZipCodeRangeDTO []";
	}
}
