package com.viviquity.core.storage;

import java.io.File;
import java.net.URL;

public interface StorageManager {

    public URL save(File file) throws FatbelliesIOException;

}
