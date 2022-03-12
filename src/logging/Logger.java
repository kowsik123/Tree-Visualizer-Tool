package logging;

import java.util.ArrayList;

public class Logger{
    ArrayList<Log> log;
    public Logger(){
        log=new ArrayList<>();
    }
    public void addLog( Log l ){
        log.add(l);
    }

    @Override
    public String toString() {
        StringBuilder str= new StringBuilder();
        for (Log i : log){
           str.append(i.toString()).append("\n");
        }
        return str.toString();
    }

//    public static void main(String[] args) throws InterruptedException {
//        Logger logger = new Logger();
//        logger.addLog( new Log("push","10") );
//        logger.addLog( new Log("pop","20") );
//        System.out.println( logger );
//    }
}
