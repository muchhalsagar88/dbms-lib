package edu.dmbs.library.test.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import edu.dbms.library.db.DBUtils;

public class TestDataLoader {

	private static final String TEST_DATA_PATH = "Data/TestData";

	public static void main(String[] args){
		try {
			loadTestData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static boolean loadTestData() throws IOException{
		File testDataDirectory = null;
		ArrayList<String> csvFiles = new ArrayList<String>();
		ArrayList<String> filesSeq = new ArrayList<String>();
		BufferedReader br = null;
		try{
			String testDataDirectoryPath = TEST_DATA_PATH.replaceAll("/", "\\"+File.separator);
			testDataDirectory = new File(testDataDirectoryPath);
			if(!testDataDirectory.exists())
				throw new FileNotFoundException();

			if(!testDataDirectory.isDirectory())
				throw new Exception("Invalid Test Data Directory");

			String[] files = testDataDirectory.list();
			for (String fileName : files) {
				if(!(fileName.toLowerCase().endsWith(".csv")))
					continue;
				if(fileName.equalsIgnoreCase("ORDER.csv"))
					br = new BufferedReader(new FileReader(testDataDirectory.getAbsolutePath()+File.separator+fileName));
				else
					csvFiles.add(testDataDirectory.getAbsolutePath()+File.separator+fileName);
			}

			if(br==null){
				filesSeq.addAll(csvFiles);
			}
			else
			{
				String line = null;
				while ((line = br.readLine()) != null) {
					filesSeq.add(testDataDirectory.getAbsolutePath()+File.separator+line.trim().replaceAll("\"", "")+".csv");
				}
			}

			csvFiles.removeAll(filesSeq);

			String DEFAULT_PERSISTENCE_UNIT_NAME = "main";

			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
					DEFAULT_PERSISTENCE_UNIT_NAME, DBUtils.getPropertiesMap());

			EntityManager entitymanager = emfactory.createEntityManager( );


			for (String file : filesSeq) {
				entitymanager.getTransaction( ).begin( );
				loadFileData(file, entitymanager);
				entitymanager.getTransaction().commit();
			}
			for (String file : csvFiles) {
				entitymanager.getTransaction( ).begin( );
				loadFileData(file, entitymanager);
				entitymanager.getTransaction().commit();
			}


			entitymanager.close();
			emfactory.close();

			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			if(br!=null)
				br.close();
		}
	}

	private static boolean loadFileData(String file, EntityManager em) throws IOException, InterruptedException{
		File csvFile = new File(file);
		if(!csvFile.exists())
			throw new FileNotFoundException(file);

		String tableName = csvFile.getName().substring(0,csvFile.getName().lastIndexOf("."));

		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		for (CSVRecord record : records) {
			StringBuffer buffer = new StringBuffer("INSERT INTO ");
			buffer.append(tableName);
			buffer.append(" VALUES(");
			int size = record.size();
			for(int i = 0;i < size;i++){

				boolean date = false;
				String value = record.get(i);

				if(value.indexOf("TO_DATE")==0)
					date = true;

				if(value.indexOf("'")>-1 && !date)
					value = record.get(i).replaceAll("'", "''");

				if(!date)
					buffer.append("'");

				buffer.append(value);

				if(!date)
					buffer.append("'");


				if(i<size-1)
					buffer.append(", ");
				else
					buffer.append(")");
			}
			System.out.println(buffer.toString());

			em.createNativeQuery(buffer.toString()).executeUpdate();

		}

		return true;
	}

}
