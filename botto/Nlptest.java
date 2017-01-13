import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.util.*;
import java.io.*;

public class Nlptest{
	public static void main(String[]args){
		DoccatModel model = null;
		InputStream dataIn = null;
		try {
		  dataIn = new FileInputStream("en-sentiment.train");
		  ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
		  ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		  model = DocumentCategorizerME.train("en", sampleStream);
		}
		catch (IOException e) {
		  // Failed to read or parse training data, training failed
		  e.printStackTrace();
		}
		finally {
		  if (dataIn != null) {
		    try {
		      dataIn.close();
		    }
		    catch (IOException e) {
		      // Not an issue, training already finished.
		      // The exception should be logged and investigated
		      // if part of a production system.
		      e.printStackTrace();
		    }
		  }
		}
	}
}
