package com.fazziclay.javaworldtools;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.PolarWorld;
import net.hollowcube.polar.PolarWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws IOException {
        OptionParser parser = new OptionParser();
        parser.accepts("source", "Path to source world file/directory").withRequiredArg().ofType(String.class).required();
        parser.accepts("dest", "Path to destination file/directory").withRequiredArg().ofType(String.class).required();
        parser.accepts("mode", "Mode of work").withRequiredArg().ofType(String.class).defaultsTo("anvil->polar").required();
        parser.accepts("help", "Print help").forHelp();

        OptionSet set = null;
        try {
            set = parser.parse(args);
        } catch (OptionException optionException) {
            System.out.println(optionException.getMessage());
            parser.printHelpOn(System.out);
            System.exit(-1);
            return;
        }
        if (set.has("help")) {
            parser.printHelpOn(System.out);
            System.exit(0);
        }

        String mode = (String) set.valueOf("mode");
        File source = new File((String) set.valueOf("source"));
        File dest = new File((String) set.valueOf("dest"));

        switch (mode) {
            case "anvil->polar", "anvilToPolar" -> anvilToPolar(source, dest);
            default -> {
                System.out.println("Unknown mode: " + mode + "; Use one of this: anvil->polar");
                System.exit(0);
            }
        }

        System.out.println("Program done success!");
    }

    private static void anvilToPolar(File source, File dest) throws IOException {
        System.out.println("Starting convert anvil to polar... Wait please...");
        PolarWorld polarWorld = AnvilPolar.anvilToPolar(source.toPath());
        byte[] write = PolarWriter.write(polarWorld);
        Files.write(dest.toPath(), write, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        System.out.println("Convert anvil -> polar success!");
    }
}