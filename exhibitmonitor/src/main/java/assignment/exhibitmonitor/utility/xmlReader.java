package assignment.exhibitmonitor.utility;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import assignment.exhibitmonitor.Field;
import assignment.exhibitmonitor.InputFile;
import assignment.exhibitmonitor.OutputFile;

public class xmlReader {

	private static Document doc;
	static String file;

	public xmlReader() {}

	public void readXML(String file) {
		this.file = file;
	}

	public static void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			doc = db.parse(file);

			parseDocument();

		}catch(ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
			//pce.printStackTrace();
		}catch(SAXException se) {
			System.out.println(se.getMessage());
			//se.printStackTrace();
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			//ioe.printStackTrace();
		}
	}


	private static void parseDocument(){

		Element docEle = doc.getDocumentElement();
		System.out.println(docEle.getNodeName()); //prints exhibit, the root element


		NodeList inputFileList = docEle.getElementsByTagName("inputfile");
		if(inputFileList != null && inputFileList.getLength() > 0) {
			for(int i = 0 ; i < inputFileList.getLength();i++) {

				Element inputfileElement = (Element)inputFileList.item(i);
				String inputfileName = inputfileElement.getAttribute("name");
				System.out.print("inputfile Name: " + inputfileName + " ");

				String inputfileTime = inputfileElement.getAttribute("time");
				System.out.print("inputfile Time: " + inputfileTime + " ");
				
				String inputfileGracePeriod = inputfileElement.getAttribute("grace-period");
				System.out.println("inputfile Grace Period: " + inputfileGracePeriod + " ");
				
				String[] time = inputfileTime.split(":");
				int minute = Integer.parseInt(time[1]) + Integer.parseInt(inputfileGracePeriod);
				int hour = Integer.parseInt(time[0]);
				while(minute>60) {
					hour += 1;
					minute -=60;
				}
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.SECOND, 0);
				Date inputfileDate = calendar.getTime();
				
				System.out.println(inputfileDate);
				
				List<Field> fieldList = new ArrayList<Field>();
				
				NodeList structureList = inputfileElement.getElementsByTagName("structure");
				if(structureList != null && structureList.getLength() > 0) {
					for(int j=0 ; j< structureList.getLength(); j++) {

						Element structureElement = (Element)structureList.item(j);

						NodeList children = structureElement.getChildNodes();
						for(int k=0; k< children.getLength(); k++) {
							Node child = children.item(k);
							if(child.getNodeType() == Node.ELEMENT_NODE)
							{
								String structureField = child.getNodeName(); //field
								System.out.print(structureField + " ");
								
								String name = null;
								String type = null;
								
								for(int l=0; l<child.getAttributes().getLength(); l++) {
									Node attribute = child.getAttributes().item(l);
									
									
									if(attribute.getNodeName().equals("name")) {
										name = attribute.getNodeValue();	
									}
									
									if(attribute.getNodeName().equals("type")) { 
										type = attribute.getNodeValue();
									}
									
								

									String attributeDetails = attribute.getNodeName() + ": " + attribute.getNodeValue();

									System.out.print(attributeDetails + " ");
									
									
								}
								System.out.print("\n");
								fieldList.add(new Field(name, type));
							}
						}
					}

				}
				InputFile inputfile = new InputFile(inputfileName, inputfileDate, fieldList);
				ApplicationContext.inputFileList.add(inputfile);
			}
		}

		NodeList outputFileList = docEle.getElementsByTagName("outputfile");
		if(outputFileList != null && outputFileList.getLength() > 0) {
			for(int i = 0 ; i < outputFileList.getLength();i++) {

				Element outputfileElement = (Element)outputFileList.item(i);
				String outputfileName = outputfileElement.getAttribute("name");
				System.out.print("outputfile Name: " + outputfileName + " ");

				String outputfileTime = outputfileElement.getAttribute("time");
				System.out.println("outputfile Time: " + outputfileTime + " ");
				String[] time = outputfileTime.split(":");
				int minute = Integer.parseInt(time[1]);
				int hour = Integer.parseInt(time[0]);
				Calendar calendar1 = Calendar.getInstance();
				calendar1.set(Calendar.MINUTE, minute);
				calendar1.set(Calendar.HOUR_OF_DAY, hour);
				calendar1.set(Calendar.SECOND, 0);
				Date outputfileDate = calendar1.getTime(); 
				
				List<String> dependencies = new ArrayList<String>();
				
				NodeList dependencyList = outputfileElement.getElementsByTagName("dependency");
				if(dependencyList != null && dependencyList.getLength() > 0) {
					for(int j =0; j< dependencyList.getLength(); j++) {

						Element dependencyElement = (Element)dependencyList.item(j);
						String dependencyFile = dependencyElement.getAttribute("file");
						System.out.println("dependency file: " + dependencyFile + " ");
						
						dependencies.add(dependencyFile);
					}
				}
				OutputFile outputfile = new OutputFile(outputfileName, outputfileDate, dependencies);
				ApplicationContext.outputFileList.add(outputfile);
			}
			
		}
		
	}
}
				
