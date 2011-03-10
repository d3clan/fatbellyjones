package com.viviquity.core.image;

import java.io.File;
import java.io.IOException;

public interface ImageCreator {

    public File createImage(String text) throws IOException;

}
