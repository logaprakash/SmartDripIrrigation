package alpha.smartdripirrigation;

import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by loga on 3/15/2017.
 */

public class rover extends TableServiceEntity {

    private String password;
    private String auth="1232nnds12";
    public rover(){
    }
    public rover(String name,String pass){
        this.password = pass;
        this.rowKey = name;
        this.partitionKey="public";
    }
    public String getRoverName(){
        return rowKey;
    }
    public String getPass(){
        return password;
    }
    public String getAuth(){
        return auth;
    }
}
