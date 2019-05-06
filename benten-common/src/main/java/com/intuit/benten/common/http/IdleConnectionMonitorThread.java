/**
 * 
 */
package com.intuit.benten.common.http;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Idle Connection Monitor Thread - to check sanity of connections
 * 
 * @author jkuria
 */
class IdleConnectionMonitorThread extends Thread {
	private static final Logger LOG = LoggerFactory
			.getLogger(IdleConnectionMonitorThread.class);

	private final PoolingHttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	private long reapInterval;

	private long idleConnectionsTimeout;

	public IdleConnectionMonitorThread(
            PoolingHttpClientConnectionManager connMgr, long reapInterval,
            long idleConnectionsTimeout) {
		super();
		this.connMgr = connMgr;
		this.reapInterval = reapInterval;
		this.idleConnectionsTimeout = idleConnectionsTimeout;

	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					LOG.trace("Connection eviction thread running every {} ms",
							reapInterval);
					wait(reapInterval);
					// Close expired connections
					connMgr.closeExpiredConnections();
					// Optionally, close connections
					// that have been idle longer than 30 sec
					LOG.trace("Closing idle connections older than {} ms",
							idleConnectionsTimeout);
					connMgr.closeIdleConnections(idleConnectionsTimeout,
							TimeUnit.MILLISECONDS);
				}
			}
		} catch (InterruptedException ex) {
			// terminate
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
