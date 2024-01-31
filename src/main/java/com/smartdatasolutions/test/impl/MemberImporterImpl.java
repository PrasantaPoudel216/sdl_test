package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberImporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MemberImporterImpl implements MemberImporter {

	@Override
	public List< Member > importMembers( File inputFile ) throws Exception {
		List<Member> memberList = new ArrayList<>();
		//reading file through buffered reader as it stores in system buffer
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			//reading each line and checking if line is last line or not
			while ((line = br.readLine()) != null) {
				//spliting data according to given length mentioned in txt file
				memberList.add(new Member(
						line.substring(0, 12).trim(),
						line.substring(12, 37).trim(),
						line.substring(37, 62).trim(),
						line.substring(62, 92).trim(),
						line.substring(92, 112).trim(),
						line.substring(112, 116).trim(),
						line.substring(116).trim()));

			}
			//returning lists of members
			return memberList;
		}

	}

}
