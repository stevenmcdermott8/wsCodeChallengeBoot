package com.steven.ws.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.steven.ws.bo.ZipCodeRangeBO;
import com.steven.ws.bo.ZipRangeBO;
import com.steven.ws.dto.ZipCodeRangeDTO;
import com.steven.ws.dto.ZipRangeDTO;
import com.steven.ws.impl.WsCodeChallengeManager;

@RestController
@RequestMapping(path = "/zipRange")
public class WsCodeChallengeController {

	private final Logger logger = LoggerFactory.getLogger(WsCodeChallengeController.class);

	@Autowired
	private WsCodeChallengeManager wsCodeChallengeManager;

	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<List<List<String>>> getRangeWithQueryParameters(
			@RequestParam(required = true, name = "zipCodeRanges") String zipCodeRanges) {
		logger.info("Getting request by QUERY parameter to retrieve minimum ranges required with input {}",
				zipCodeRanges);

		if (StringUtils.isEmpty(zipCodeRanges)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<List<String>> returnedList = wsCodeChallengeManager
				.retrieveMinimumRangeSetFromArray(convertPipeDelimitedToList(zipCodeRanges));
		logger.info("Returned list of ranges {}", returnedList);

		return ResponseEntity.ok(returnedList);
	}

	@GetMapping(path = "/{zipCodeRanges}")
	public ResponseEntity<List<List<String>>> getRangeWithPathParameters(
			@PathVariable(required = true, name = "zipCodeRanges") String zipCodeRanges) {
		logger.info("Getting request by PATH parameter to retrieve minimum ranges required with input {}",
				zipCodeRanges);

		if (StringUtils.isEmpty(zipCodeRanges)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<List<String>> returnedList = wsCodeChallengeManager
				.retrieveMinimumRangeSetFromArray(convertPipeDelimitedToList(zipCodeRanges));
		logger.info("Returned list of ranges {}", returnedList);

		return ResponseEntity.ok(returnedList);
	}

	@PostMapping(path = "")
	public ResponseEntity<List<List<String>>> getRangeWithRequestObject(@RequestBody ZipRangeDTO zipRangeDTO) {

		if (zipRangeDTO == null || CollectionUtils.isEmpty(zipRangeDTO.getZipCodeRanges())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		logger.info("Getting request by POST with JSON object to retrieve minimum ranges required with input {}",
				zipRangeDTO.getZipCodeRanges());
		ZipRangeBO zipRangeBO = convertZipRangeDTOtoBO(zipRangeDTO);
		List<List<String>> returnedList = wsCodeChallengeManager.retrieveMinimumRangeSetFromArray(zipRangeBO);
		logger.info("Returned list of ranges {}", returnedList);

		return ResponseEntity.ok(returnedList);
	}

	private List<List<String>> convertPipeDelimitedToList(String pipeDelimited) {

		List<List<String>> list = new ArrayList<>();

		for (String item : pipeDelimited.split("\\|")) {
			list.add(Arrays.asList(item.split(",")));
		}

		return list;

	}

	private ZipRangeBO convertZipRangeDTOtoBO(ZipRangeDTO zipRangeDTO) {
		ZipRangeBO zipRangeBO = new ZipRangeBO();
		List<ZipCodeRangeBO> ranges = new ArrayList<>();
		for (ZipCodeRangeDTO rangeDto : zipRangeDTO.getZipCodeRanges()) {
			ZipCodeRangeBO zipCodeRangeBO = new ZipCodeRangeBO();
			zipCodeRangeBO.setZipRange(rangeDto.getZipRange());
			ranges.add(zipCodeRangeBO);
		}
		zipRangeBO.setZipCodeRanges(ranges);
		return zipRangeBO;
	}
}
