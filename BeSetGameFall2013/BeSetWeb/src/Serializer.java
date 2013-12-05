/******************************************************************************
 * 
 *	Serializer.java
 *
 *	By Cullen Foster - 8/21/2013
 *
 ***/


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.logging.Logger;



//=============================================================================
public final class Serializer 
{
    private static RandomAccessFile     accessfile      = null;
    private static FileOutputStream     outputstream    = null;
    private static FileInputStream      inputstream     = null;
    private static ObjectOutputStream   objoutstream    = null;
    private static ObjectInputStream    objinstream     = null;
    
  //Logging setup start
    
    
   /* 
    * set up the logger in the serverlet main
    * 
    * private final static Logger LOGGER = Logger.getLogger(KnockKnock.class .getName());	//Logging 
    */


    //=========================================================================
    public boolean DumpToFile(String filename, Object outclass) throws IOException //Added "throws IOException" by Jacob Postema
    {
        if((filename.length() > 0) 
                && (outclass instanceof java.io.Serializable))
        {
            CreateFolderIfNeeded(filename);
            		
            try
            {			
                accessfile      = new RandomAccessFile(filename, "rw");
                outputstream    = new FileOutputStream(accessfile.getFD());
                objoutstream    = new ObjectOutputStream(outputstream);
                			
                objoutstream.writeObject(outclass);
                			
                objoutstream.close();
                outputstream.close();
                accessfile.close();
            }
            catch(IOException ex)
            {
            	//LOGGER.warning(filename+ " not writeable: " + ex.toString());		// Added By Jacob Postema
                throw new IOException(ex);		// Serializer now throws the Exceptions so they can be caught by the Main and handled. By Jacob Psotema
            }	
            return true;
        }
    	
        return false;
    }
    
    //=========================================================================
    public Object ReadToClass(String filename) throws IOException //Added "throws IOException" by Jacob Postema
    {
        if(filename.length() > 0)
        {
            try
            {
                accessfile      = new RandomAccessFile(filename, "rw");
                inputstream	    = new FileInputStream(accessfile.getFD());
                objinstream	    = new ObjectInputStream(inputstream);
                	
                Object object = objinstream.readObject();
                	
                objinstream.close();
                inputstream.close();
                accessfile.close();
                
                return object;
            }
            catch(IOException ex)
            {
            	//LOGGER.warning(filename+ " not found: " + ex.toString());		// Added By Jacob Postema
                throw ex; // Serializer now throws the Exceptions so they can be caught by the Main and handled. By Jacob Psotema
            } 
            catch(ClassNotFoundException ex) 
            {	
                ex.printStackTrace();
            }
        }
    
        return null;
    }
    
    //=========================================================================
    private void CreateFolderIfNeeded(String path)
    {
        String filepath = null;
        File   file     = null;
        	
        {
            // We need to extract the folder path from the
            // desired user file name.
            file = new File(path);
            String absolute = file.getAbsolutePath();
            filepath = absolute.substring(0, absolute.lastIndexOf(File.separator));			
        }
    
        if(filepath != null)
        {
            file = new File(filepath);
    	
            boolean success = file.mkdirs();
    	
            if(success)
            {
                System.out.println("Created the directory/directories" + filepath);
            }
        }
    }
}

// End of the file.
