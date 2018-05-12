package com.wz.common.tools.spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

public class GzipEntityWrapper extends HttpEntityWrapper {

	public GzipEntityWrapper(HttpEntity wrapped) {

		super(wrapped);

	}

	public InputStream getContent() throws IOException, IllegalStateException {

		InputStream wrappedin = wrappedEntity.getContent();

		return new GZIPInputStream(wrappedin);
	}

	public long getContentLength() {

		return -1;

	}

}
