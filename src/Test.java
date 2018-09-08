import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Test{
	
	public static String metadataType = null;
	public static String metadataTypeName = null;
	public static String packageNameSpacePrefix = null;
	public static String includePackage = "false";
	
	
	public static void main(String[] args) throws IOException{
		/*if(args.length > 0){
			metadataTypeName = args[0];
			packageNameSpacePrefix = args[1];
		}*/
		readPropertiesFile();
		
		if(metadataTypeName != null && metadataTypeName != "" && packageNameSpacePrefix != null && packageNameSpacePrefix != ""){
			Scanner in = new Scanner(new FileReader(metadataTypeName+".txt"));
			StringBuilder sb = new StringBuilder();
			while(in.hasNextLine()) {
				String line = in.nextLine();
				if("true".equalsIgnoreCase(includePackage)){
					if(line.contains("FileName: "+metadataType+"/"+packageNameSpacePrefix)){
						String reqLine = line.substring(line.indexOf(packageNameSpacePrefix), line.indexOf("."));
						sb.append("<members>"+reqLine+"</members>"+"\n");
					}
				}else{
					if(line.contains("FileName: "+metadataType+"/") && !line.contains(packageNameSpacePrefix)){
						String reqLine = line.substring(line.indexOf("/")+1, line.indexOf("."));
						sb.append("<members>"+reqLine+"</members>"+"\n");
					}
				}
				
			}
			writeUsingOutputStream(sb);
			in.close();
			System.out.println("MetadataList generated in Output"+metadataTypeName+".txt file.");
		}else{
			System.out.println("Please provide the metadataTypeName & packageNameSpacePrefix");
		}
	}
	
	private static void writeUsingOutputStream(StringBuilder data) {
		File file = new File("Output"+metadataTypeName+".txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	private static void readPropertiesFile(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("getMetadataConfig.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			metadataType = prop.getProperty("metadataType");
			metadataTypeName = prop.getProperty("metadataTypeName");
			packageNameSpacePrefix = prop.getProperty("packageNameSpacePrefix");
			includePackage = prop.getProperty("includePackage");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}