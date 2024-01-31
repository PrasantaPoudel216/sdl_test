package com.smartdatasolutions.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public abstract class MemberFileConverter {
	/*
	 * NOTE: Do not modify this class
	 */
	protected abstract MemberExporter getMemberExporter( );

	protected abstract MemberImporter getMemberImporter( );

	protected abstract List< Member > getNonDuplicateMembers( List< Member > membersFromFile );

	protected abstract Map< String, List< Member >> splitMembersByState( List< Member > validMembers );

	public void convert( File inputMemberFile, String outputFilePath, String outputFileName ) throws Exception {

		/*
		 * Read :
		 */
		//firstly getting importer object created in main class
		MemberImporter memberImporter = getMemberImporter( );
		//importing data from file and returning lists of unfiltered data from file
		List< Member > membersFromFile = memberImporter.importMembers( inputMemberFile );

		/*
		 * Filter :
		 */
		///filtering data and including only unique result and getting in lists
		List< Member > validMembers = getNonDuplicateMembers( membersFromFile );
		//splitting members according to states
		Map< String, List< Member >> membersFilteredByState = splitMembersByState( validMembers );

		/*
		 * Write :
		 */
		//looping through each map list
		for ( Map.Entry< String, List< Member >> map: membersFilteredByState.entrySet( ) ) {

			///getting members states
			String state = map.getKey( );
			//getting lists of members in each states
			List< Member > membersPerState = map.getValue( );
			///setting output file name with state name as well as user input name
			File outputFile = new File( outputFilePath + File.separator + state + "_" + outputFileName );
			//getting memberexporter object
			MemberExporter exporter = getMemberExporter( );
			//writting in each separate files
			writeMembers( outputFile, exporter, membersPerState );

		}

	}

	private void writeMembers( File outputFile, MemberExporter exporter, List< Member > members ) throws IOException {
		//setting writer objects
		Writer writer = new FileWriter( outputFile ,true);
		//creating headers
		writer.write("id,first_name,last_name,address,city,zip");
		///one line down
		writer.write("\n");
		//iterating through members lists
		for ( Member member: members ) {
			exporter.writeMember( member, writer );
		}
		//flushing to write in system through buffer
		writer.flush( );
		//closing resources
		writer.close( );
	}

}
