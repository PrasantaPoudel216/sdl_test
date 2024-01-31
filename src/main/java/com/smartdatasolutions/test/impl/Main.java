package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberExporter;
import com.smartdatasolutions.test.MemberFileConverter;
import com.smartdatasolutions.test.MemberImporter;

import java.io.File;
import java.util.*;

public class Main extends MemberFileConverter {

	MemberImporter memberImporter=new MemberImporterImpl();
	MemberExporter memberExporter=new MemberExporterImpl();

	//setting member exporter object
	@Override
	protected MemberExporter getMemberExporter( ) {
		return memberExporter;
	}
	//setting member importer object
	@Override
	protected MemberImporter getMemberImporter( ) {

		return memberImporter;
	}

	@Override
	protected List< Member > getNonDuplicateMembers( List< Member > membersFromFile ) {
		/////using set with hash set as it only includes unique elements by checking hash and values both
		Set<Member> uniqueMembers = new HashSet<>(membersFromFile);
		//returning in list
		return new ArrayList<>(uniqueMembers);
	}

	@Override
	protected Map< String, List< Member >> splitMembersByState( List< Member > validMembers ) {
		//creating new hash map
		Map<String, List<Member>> separatedStateMember = new HashMap<>();
		// going through each unique members lists and adding in hash map by checking their states
		for(Member member:validMembers){
			//checking if state already present or not adding new arraylist for new state that is not present
			separatedStateMember.putIfAbsent(member.getState(),new ArrayList<>());
			//adding member data in already present hash map key
			separatedStateMember.get(member.getState()).add(member);
		}
		//returning result
		return separatedStateMember;
	}

	public static void main( String[] args ) {
		Scanner scan=new Scanner(System.in);

		try {

			System.out.println("enter input file Name with path"); //getting input file name with path
			String inputFileName=scan.nextLine();
			System.out.println("enter output file path"); /// getting output file path
			String outputFilePath=scan.nextLine();
			System.out.println("enter output file name"); //getting output file name
			String outputFileName=scan.nextLine();

			if(!outputFileName.contains(".")) { //checking if output file contains extension or not
				outputFileName=outputFileName.concat(".csv");  //if not present extension adding it
			}


			File inputMemberFile=new File(inputFileName);   //importing file
			//converting from txt to csv
			new Main().convert(inputMemberFile,outputFilePath,outputFileName );
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(scan!=null) {
				scan.close(); //closing resources
			}
		}
	}



}
