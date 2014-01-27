package codechicken.lib.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import net.minecraftforge.common.Configuration.UnicodeInputStreamReader;

public class ConfigFile extends ConfigTagParent
{    
    public String defaultEncoding = "UTF-8";

    public ConfigFile(File file)
    {
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
            	throw new IOException("Failed to create configuration file " + file.getCanonicalPath());
            }
            
            if(!file.canRead()) {
            	throw new IOException("Failed to read configuration file " + file.getCanonicalPath());
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
        this.file = file;
        newlinemode = 2;
        loadConfig();
    }
    
    private void loadConfig()
    {
        loading = true;
        BufferedReader reader;
        
		try {
			reader = new BufferedReader(new UnicodeInputStreamReader(new FileInputStream(file), defaultEncoding));

			while(true)
            {
                reader.mark(2000);
                String line = reader.readLine();
                if(line != null && line.startsWith("#"))
                {
                    if(comment == null || comment.equals(""))
                        comment = line.substring(1);
                    else
                        comment = comment+"\n"+line.substring(1);
                }
                else
                {
                    reader.reset();
                    break;
                }
            }
            loadChildren(reader);
            reader.close();
            
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        
        loading = false;
    }
    
    @Override
    public ConfigFile setComment(String header)
    {
        super.setComment(header);
        return this;
    }
    
    @Override
    public ConfigFile setSortMode(int mode)
    {
        super.setSortMode(mode);
        return this;
    }
    
    @Override
    public String getNameQualifier()
    {
        return "";
    }
    
    public static String readLine(BufferedReader reader) throws IOException
    {
        String line = reader.readLine();
        if(line != null)
            return line.replace("\t", "");
        return line;
    }
    
    public static String formatLine(String line)
    {
        line = line.replace("\t", "");
        if(line.startsWith("#"))
        {
            return line;
        }
        else if(line.contains("="))
        {
            line = line.substring(0, line.indexOf("=")).replace(" ", "")+line.substring(line.indexOf("="));
            return line;
        }
        else
        {
            line = line.replace(" ", "");
            return line;
        }
    }
    
    public static void writeLine(PrintWriter writer, String line, int tabs)
    {
        for(int i = 0; i < tabs; i++)
            writer.print('\t');
        
        writer.println(line);
    }
    
    public void saveConfig()
    {
        if(loading)
            return;
        
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(file, defaultEncoding);
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
		}
        
        writeComment(writer, 0);
        ConfigFile.writeLine(writer, "", 0);
        saveTagTree(writer, 0, "");
        writer.flush();
        writer.close();
    }
    
    public boolean isLoading()
    {
        return loading;
    }
    
    public File file;
    private boolean loading;
    
    public static final byte[] lineend = new byte[]{0xD, 0xA};
}
