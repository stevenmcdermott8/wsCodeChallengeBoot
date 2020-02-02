package com.steven.ws.dto;

import java.util.List;

public class ZipRangeDTO {
	private List<ZipCodeRangeDTO> zipCodeRanges;

	public List<ZipCodeRangeDTO> getZipCodeRanges() {
		return zipCodeRanges;
	}

	public void setZipCodeRanges(List<ZipCodeRangeDTO> zipCodeRanges) {
		this.zipCodeRanges = zipCodeRanges;
	}

	@Override
	public String toString() {
		return "ZipRangeDTO [list=" + zipCodeRanges + "]";
	}

}
