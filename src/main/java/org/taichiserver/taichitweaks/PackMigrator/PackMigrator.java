package org.taichiserver.taichitweaks.PackMigrator;

import java.nio.file.Path;

public class PackMigrator {
    private static PackMigrator Process;
    private Path newInstancePath;
    private boolean processing;
    public PackMigrator(Path path){
        this.newInstancePath = path;
        this.processing = false;
    }
    public boolean execute(){
        if(Process.isProcessing()) return false;
        this.processing = true;
        Process = this;

        //unzip.init("","");

        return true;
    }

    public boolean isProcessing() {
        return processing;
    }
}
