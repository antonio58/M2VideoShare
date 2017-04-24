package Server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Utilizador on 19/04/2017.
 */
public class User {

    int id;
    String name;
    String email;
    String password;
    List<String> channels;

    User(){
        this.id = 0;
        this.name = "";
        this.email = "";
        this.password = "";
        channels = Collections.emptyList();
    }

    User(int i, String n, String e, String p, List<String> c){
        this.id = i;
        this.name = n;
        this.email = e;
        this.password = p;
        this.channels = c;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName(){return name;}

    public boolean checkPassword(String p){
        if(p.equals(this.password))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", channels=" + channels +
                '}';
    }

    public void setID(int i){ this.id = i;}
    public void setName(String n){ this.name = n;}
    public void setEmail(String n){ this.email = n;}
    public void setPassword(String n){ this.password = n;}

}
