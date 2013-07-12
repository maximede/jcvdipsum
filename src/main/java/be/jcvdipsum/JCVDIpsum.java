package be.jcvdipsum;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import be.jcvdipsum.model.Citation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: deram
 * Date: 11/07/13
 * Time: 17:23
 */
public class JCVDIpsum {

	private static final String CITATION_FILE = "citations.csv";
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
						CITATION_FILE)));

		CSVReader cvsReader = new CSVReader(reader, ',', '"',1);

		citations = csv.parse(strat,cvsReader);

		reader.close();

		Collections.shuffle(citations);

		for (Citation citation : citations) {
			citation.setBody(StringUtils.strip(citation.getBody()));
			citation.setTitle(StringUtils.strip(citation.getTitle()));
		}
	}

	public String getRandomCitationBody() {
		return getRandomCitation().getBody();
	}

	private Citation getRandomCitation() {
		return citations.get(random.nextInt(citations.size() - 1));
	}

	public String getCitations(int nbr) {
		StringBuilder b = new StringBuilder();

		for (int i = 0; i < nbr; i++) {
			if (i != 0) {
				b.append("\n");
			}
			b.append(getRandomCitationBody());
		}

		return b.toString();
	}

	public static void main(String[] args) throws IOException {
		JCVDIpsum j = new JCVDIpsum();

		System.out.println(j.getRandomCitationBody());

		System.out.println(j.getCitations(5));


	}
}
