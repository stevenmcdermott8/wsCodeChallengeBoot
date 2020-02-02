package com.steven.ws.bo;

import java.util.List;

public class ZipCodeRangeBO {
	private List<String> zipRange;

	@Override
	public String toString() {
		return "ZipCodeRangeDTO []";
	}

	public List<String> getZipRange() {
		return zipRange;
	}

	public void setZipRange(List<String> zipRange) {
		this.zipRange = zipRange;
	}
}
