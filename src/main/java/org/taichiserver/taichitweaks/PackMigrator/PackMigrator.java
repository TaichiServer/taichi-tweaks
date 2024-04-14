package org.taichiserver.taichitweaks.PackMigrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.taichiserver.taichitweaks.config.PackMigratorGui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PackMigrator extends Thread {
    private static PackMigrator Process;
    private Path nowInstancePath;
    private Path newInstancePath;
    private boolean processing;
    public PackMigrator(Path path){
        this.nowInstancePath = Paths.get(".");
        this.newInstancePath = path;
        this.processing = false;
    }
    public void run(){
        System.out.println("Get Order");
        if( !Files.exists(newInstancePath) ) return;
        if( !newInstancePath.toString().matches("^.+\\.minecraft$") ) return;
        if( !(Process==null || !Process.isProcessing()) ) return;
        System.out.println("Start Migrate:");
        System.out.println(newInstancePath.toString());
        this.processing = true;
        Process = this;
        try {
            mods();
            config();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish");
        this.processing = false;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void mods() throws IOException {
        Path nowModsPath = nowInstancePath.resolve("mods");

        Path cachePath = Paths.get("./cache");
        cachePath.toFile().mkdirs();
        cachePath = cachePath.resolve("pack_migrator");


        if( !Files.exists(nowModsPath) ) return;
        File[] nowModFiles = nowModsPath.toFile().listFiles();
        List<Mod> nowMods = getMods(nowModFiles, cachePath);

        Path newModsPath = newInstancePath.resolve("mods");
        if( !Files.exists(newModsPath) ) return;
        File[] newModFiles = newModsPath.toFile().listFiles();
        List<Mod> newMods = getMods(newModFiles, cachePath);

        for(Mod nowMod : nowMods){
            for(Mod newMod : newMods){
                if( nowMod.getId().equals(newMod.getId()) ) {
                    if (nowMod.isEnabled() && !newMod.isEnabled()) newMod.toEnabled();
                    if (!nowMod.isEnabled() && newMod.isEnabled()) newMod.toDisabled();
                }
            }
        }

        //System.out.println(modFiles);
        //unzip(new File(""));
    }

    class Mod {
        private JsonNode config;
        private String mod_id;
        private Path path;
        private boolean isEnabled;

        public Mod(Path path, JsonNode config){
            if(path.toString().matches("^.+\\.jar$")){
                this.isEnabled = true;
            } else if (path.toString().matches("^.+\\.jar.disabled$")) {
                this.isEnabled = false;
            } else {
                return;
            }

            this.path = path;
            this.config = config;
            this.mod_id = config.get("id").asText();
        }

        public JsonNode getConfig(String str){
            return config.get(str);
        }

        public String getId(){
            return mod_id;
        }

        public Path getPath(){
            return path;
        }

        public boolean isEnabled() {
            return isEnabled;
        }
        public void toEnabled(){
            String renamedPath = path.toString().replace("\\.disabled$","");
            path.toFile().renameTo(Paths.get(renamedPath).toFile());
        }
        public void toDisabled(){
            String renamedPath = path.toString()+".disabled";
            path.toFile().renameTo(Paths.get(renamedPath).toFile());
        }
    }
    public List<Mod> getMods(File[] nowModFiles, Path cachePath) throws IOException {
        List<Mod> modsList = new ArrayList<>();
        File cacheFile = cachePath.toFile();
        ObjectMapper objectMapper = new ObjectMapper();

        for (File modFile : nowModFiles) {
            cacheFile.mkdirs();

            unzip.init(modFile, cacheFile);
            Path fabric_mod_json = cachePath.resolve("fabric.mod.json");
            JsonNode json = objectMapper.readTree(fabric_mod_json.toFile());
            Mod mod = new Mod(modFile.toPath(), json);
            modsList.add(mod);

            cacheFile.delete();
        }
        return modsList;
    }

    public void config() throws IOException{
        for(Map.Entry<String, Boolean> entry : PackMigratorGui.CheckBoxListener.options.entrySet()) {
            if(!entry.getValue()) continue;
            CopyConfig(entry.getKey());
        }
    }
    private void CopyConfig(String name) throws IOException {
        File nowFile = nowInstancePath.resolve(name).toFile();
        if(!nowFile.exists()) return;

        if(nowFile.isDirectory()){
            File newFile = newInstancePath.resolve(name).toFile();
            if(!newFile.exists()) newFile.mkdir();
            CopyDir(
                    nowInstancePath.resolve(name),
                    newInstancePath.resolve(name)
            );
        } else {
            Files.copy(
                    nowInstancePath.resolve(name),
                    newInstancePath.resolve(name),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
    private void CopyDir(Path path1, Path path2) throws IOException{
        File[] folders1 = path1.toFile().listFiles();
        for(File folder1: folders1){
            String foder1name = folder1.toPath().getFileName().toString();
            Path folder2 = path2.resolve(foder1name);
            try {
                Files.copy(folder1.toPath(), folder2, StandardCopyOption.REPLACE_EXISTING);
            } catch (DirectoryNotEmptyException e){
                CopyDir(folder1.toPath(), folder2);
            }
        }
    }
}
