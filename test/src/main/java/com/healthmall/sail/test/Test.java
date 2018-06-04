package com.healthmall.sail.test;


import org.python.util.PythonInterpreter;

import java.net.URISyntaxException;
import java.util.Properties;

public class Test {
    public static void main( String[] args ) throws URISyntaxException {
        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);


        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
        interpreter.exec("print days[1];");
    }
}
