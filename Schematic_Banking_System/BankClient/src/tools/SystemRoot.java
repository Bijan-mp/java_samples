package tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11/19/12
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemRoot {

    public String[] getDIR(String file_address)throws Exception
    {
        File file = new File(file_address);

        return  file.list();
    }

    public boolean  makeDirectory(String file_address)throws Exception
    {
        File file = new File(file_address);

        return file.mkdir();
    }

    public boolean moveFile(String file_address, String file_address2)throws Exception{
        File file = new File(file_address);
        File file2 = new File(file_address2);

        return file.renameTo(file2);
    }

    public boolean delete(String file_address)throws Exception{
        File file = new File(file_address);

        return file.delete();
    }

    public boolean isHidden(String file_address)throws Exception{
        File file = new File(file_address);

        return file.isHidden();
    }

    public boolean isExist(String file_address)throws Exception{
        File file = new File(file_address);

        return file.exists();
    }

    public boolean isWriteStatusFile(String file_address)throws Exception{
        File file = new File(file_address);

        return file.canWrite();
    }

    public boolean isDirectory(String file_address)throws Exception{
        File file = new File(file_address);

        return file.isDirectory();
    }

    public boolean setReadOnlyFile(String file_address)throws Exception{
        File file = new File(file_address);

        return file.setReadOnly();
    }

    public String getLastModified(String file_address)throws Exception{
        File file = new File(file_address);
        String m = String.valueOf(file.lastModified());

        return m;
    }

    public boolean setLastModified(String file_address, String Last_modified)throws Exception{
        File file = new File(file_address);

        return file.setLastModified(Long.parseLong(Last_modified));
    }

    public String getContentFile(String file_address)throws Exception{
        String str="";

        File file = new File(file_address);
        FileReader file_reader = new FileReader(file);

        int  charcode;
        charcode=file_reader.read();

        while(charcode != -1){
            str = str + (char )charcode;
            charcode = file_reader.read();
        }

        file_reader.close();

        return str;
    }

    public boolean writeContentFile(String file_address,String content)throws Exception{
        File file=new File(file_address);
        FileWriter file_writer=new FileWriter(file);

        file_writer.write(content);
        file_writer.close();

        return true;
    }

}
