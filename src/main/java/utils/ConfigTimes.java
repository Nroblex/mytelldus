package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by anders on 11/28/15.
 */
public class ConfigTimes {

    public static void runConfiguration(){

        boolean run = true;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (run){



            System.out.print("\n\nDo you want to add (1) or create (2) configuration file ?: ");
            String answer = null;
            try {
                answer = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (answer.compareTo("1") == 0){
                Util.printMessage("Add new device to handle...");

                AddNewDevice();

                run=false;
            } else if (answer.compareTo("2") == 0){
                Util.printMessage("Creating a new config...");
                run=false;
            } else{
                Util.printMessage("\n\nWrong answer! say (1) OR (2)...\n\n");
            }


        }


    }

    //Add a new device...ask user
    private static void AddNewDevice() {

        boolean run=true;


    }

}
