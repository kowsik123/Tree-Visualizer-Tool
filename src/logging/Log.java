package logging;

import java.util.Date;

public class Log {
    String operation,data,output;
    Date logTime;
    public Log(){
        logTime = new Date();
    }
    public Log(String operation, String data){
        this();
        this.operation = operation;
        this.data = data;
        this.output = null;
    }
    public Log( String operation ) {
        this();
        this.operation = operation;
        this.data = null;
        this.output = null;
    }
    public Log(String operation , String data , String output ){
        this();
        this.operation = operation;
        this.data = data;
        this.output = output;
    }

    @Override
    public String toString() {
        return "Log{ " +
                "time='"+ logTime.toLocaleString() +"'"+
                ", operation='" + operation +"'"+
                ((data!=null)? ", data='"+data+"'" : "") +
                ((output!=null)? ", output='"+ output+"'" : "") +
                " }";
    }
}
