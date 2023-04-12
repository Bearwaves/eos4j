package com.bearwaves.eos4j;

import org.lwjgl.system.Library;
import org.lwjgl.system.Platform;

public class EOS {

    public static boolean loadLibraries() {
        try {
            switch (Platform.get()) {
                case LINUX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Linux-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
                case MACOSX:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Mac-Shipping");
                    switch (Platform.getArchitecture()) {
                        case X64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                            break;
                        case ARM64:
                            Library.loadSystem("com.bearwaves.eos4j", "eos4jarm64");
                            break;
                    }
                    break;
                case WINDOWS:
                    Library.loadSystem("com.bearwaves.eos4j", "EOSSDK-Win64-Shipping");
                    Library.loadSystem("com.bearwaves.eos4j", "eos4j64");
                    break;
            }
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

}
