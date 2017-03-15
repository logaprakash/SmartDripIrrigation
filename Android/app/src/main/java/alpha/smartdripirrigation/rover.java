package alpha.smartdripirrigation;

import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableServiceEntity;

/**
 * Created by loga on 3/15/2017.
 */

public class rover extends TableServiceEntity {

    private String roverName;
    private String password;
    private String auth;
    public rover(){
    }
    public rover(String name,String pass){
        this.roverName = name;
        this.password = pass;
    }
    public String getRoverName(){
        return roverName;
    }
    public String getPass(){
        return password;
    }
    public String getAuth(){
        return auth;
    }
}
