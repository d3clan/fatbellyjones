package com.viviquity.core.storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.model.S3Object;

public class S3StorageManagerImpl implements StorageManager {

    private Logger logger = Logger.getLogger(S3StorageManagerImpl.class);

    private final String amazonLocation;
    private final S3Service s3Service;
    private final String bucketName;

    public S3StorageManagerImpl(String amazonLocation, S3Service s3Service, String bucketName) {
	this.amazonLocation = amazonLocation;
	this.s3Service = s3Service;
	this.bucketName = bucketName;
    }

    public URL save(File file) throws FatbelliesIOException {
	S3Object ob = null;
	try {
	    ob = s3Service.putObject(bucketName, new S3Object(file));
	} catch (NoSuchAlgorithmException e) {
	    throw new FatbelliesIOException("No algorithm for saving to AWS", e);
	} catch (S3ServiceException e) {
	    throw new FatbelliesIOException("Cannot save to AWS", e);
	} catch (IOException e) {
	    throw new FatbelliesIOException("Cannot open file to save to AWS", e);
	}

	return getUrl(ob);
    }

    private URL getUrl(S3Object ob) {
	try {
	    return new URL("http://" + bucketName + "." + amazonLocation + "/" + ob.getKey());
	} catch (MalformedURLException e) {
	    logger.info("Cannot create URL", e);
	    return null;
	}
    }
}
