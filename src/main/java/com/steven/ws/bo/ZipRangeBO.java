package com.steven.ws.bo;

import java.util.List;

public class ZipRangeBO {
	private List<ZipCodeRangeBO> zipCodeRanges;

	public List<ZipCodeRangeBO> getZipCodeRanges() {
		return zipCodeRanges;
	}

	public void setZipCodeRanges(List<ZipCodeRangeBO> zipCodeRanges) {
		this.zipCodeRanges = zipCodeRanges;
	}

	@Override
	public String toString() {
		return "ZipRangeDTO [list=" + zipCodeRanges + "]";
	}

}
