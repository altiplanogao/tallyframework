package com.taoswork.tallybook.module.elevator.launcher;

import com.taoswork.tallybook.module.elevator.dataservice.ElevatorModuleDataService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public class ModuleEntry {
    private ElevatorModuleDataService moduleDataService;
    private CountDownLatch shutdown = new CountDownLatch(1);

//    public static void main(String args[]){
//        ModuleEntry entry = new ModuleEntry();
//        entry.start();
//    }

    public void start() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                moduleRun();
            }
        };
        thread.setName(this.getClass().getSimpleName());
        thread.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        try {
            do {
                String line = reader.readLine();
                if(line != null){
                    if("quit".equals(line.toLowerCase())){
                        stop();
                        exit = true;
                    }
                }
            } while (!exit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moduleRun() {
        moduleDataService = new ElevatorModuleDataService();
        try {
            shutdown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        shutdown.countDown();
        moduleDataService = null;
    }
}
