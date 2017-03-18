package alpha.smartdripirrigation;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by loga on 3/15/2017.
 */

public class rover extends TableServiceEntity {

    public String password;
    public rover(){
    }
    public rover(String name,String pass){
        password = pass;
        rowKey = name;
        partitionKey="public";
    }
    public String getRoverName(){
        return rowKey;
    }
    public String getPass(){
        return password;
    }
}
