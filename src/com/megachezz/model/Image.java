package com.megachezz.model;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Image {


	private InputStream resource;
	private URL resourceUri;

	public Image(String resourceUri) {
		this.resourceUri = this.getClass().getResource(resourceUri);
		this.resource = this.getClass().getResourceAsStream(resourceUri);
	}

	public InputStream getImageInputStream() {
		return resource;
	}

	public URL getImageUri() {
		return this.resourceUri;
	}

}
