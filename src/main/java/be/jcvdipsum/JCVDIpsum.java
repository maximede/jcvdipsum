package be.jcvdipsum;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import be.jcvdipsum.model.Citation;
import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: deram
 * Date: 11/07/13
 * Time: 17:23
 */
public class JCVDIpsum {

	private List<Citation> citations;
	private Random random = new Random();

	public JCVDIpsum() throws IOException {

		citations = new ArrayList<Citation>();

		ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
		strat.setType(Citation.class);
		String[] columns = new String[] {"title", "body"}; // the fields to bind do in your JavaBean
		strat.setColumnMapping(columns);


		CsvToBean csv = new CsvToBean();
		Reader reader = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream(
						"citations.csv")));

		CSVReader cvsReader = new CSVReader(reader, ',', '"');

		citations = csv.parse(strat,cvsReader);

		reader.close();

		Collections.shuffle(citations);
	}

	public String randomCitationBody() {
		return getRandomCitation().getBody();
	}

	private Citation getRandomCitation() {
		return citations.get(random.nextInt(citations.size() - 1));
	}

	public String getCitations(int nbr) {
		return getRandomCitation().getBody();
	}

	public static void main(String[] args) throws IOException {
		JCVDIpsum j = new JCVDIpsum();




	}
}
