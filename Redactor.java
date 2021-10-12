//Compile with [ javac Redactor.java ]
//I used [ --release 8 ] with no issues
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter; 
import java.lang.String;
import java.lang.StringBuffer;

public class Redactor{
	
	public static void pErr(String err){
		//Print error and exit process
		System.out.println("Error: " + err);
		System.out.println("Usage: Redactor [filepath] [elements,to,redact] [optional:fill string]\n");
		System.exit(0);
	}
	
	public static void main(String args[]){
	if(args.length < 2){
		pErr("Incorrect arguments");
	}
	File docu = new File(args[0]);
	if(!docu.exists()){
		pErr(args[0] + " not found.");
	}
	if (!docu.isFile()){
		pErr(args[0] + " is not a file.");
	}
	if (!docu.getAbsolutePath().endsWith(".txt")) {
		pErr("File is not a text (.txt) file.");
	}
	try{
		Scanner parse = new Scanner(docu);
		FileWriter scribe = new FileWriter("Redacted.txt");
		String[] arr = args[1].split(",");
		/*for(String e : arr){
			System.out.println(e);
		}/**/
		//pErr("Debug");
		String line, fill = "█";
		//Make sure optional fill character will not cause infinite issues
		if(args.length > 2){
			Boolean rep = true;
			for(String s : arr){
				if(args[2].contains(s)){
					rep = false;
					System.out.println("Fill string cannot contain redacted phrase.");
					break;
				}
			}
			if(rep)
				fill = args[2];
		}
		StringBuffer nLine;
		while (parse.hasNextLine()){
			line = parse.nextLine();
			nLine = new StringBuffer(line);
			for(String phrase: arr){
				while(nLine.toString().toLowerCase().indexOf(phrase.toLowerCase())!=-1){
					String x = new String(new char[phrase.length()]).replace("\0", fill);
					nLine.replace(nLine.toString().toLowerCase().indexOf(phrase.toLowerCase()), nLine.toString().toLowerCase().indexOf(phrase.toLowerCase())+phrase.length(), x);
				}
			}
			scribe.write(nLine+"\n");
		}
		scribe.close();
	}catch(Exception e){
		pErr("FileNotFoundException");
	}
	
	}

	
}
