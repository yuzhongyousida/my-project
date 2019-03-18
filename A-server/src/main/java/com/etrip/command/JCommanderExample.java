package com.etrip.command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * @author: wangteng
 * @description:
 * @date:2018/8/18
 */
public class JCommanderExample {

    @Parameter(names = { "-level", "-l" }, description = "Level of verbosity")
    private Integer verbose = 1;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;


    public static void main(String[] args) {
        try {
            JCommanderExample jCommanderExample = new JCommanderExample();
            String[] argv = { "-log", "2", "-groups", "unit" };
            JCommander commander = new JCommander();
            commander.addObject(jCommanderExample);
            commander.parse(argv);

            System.out.println(jCommanderExample.verbose);
            System.out.println(jCommanderExample.groups);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
