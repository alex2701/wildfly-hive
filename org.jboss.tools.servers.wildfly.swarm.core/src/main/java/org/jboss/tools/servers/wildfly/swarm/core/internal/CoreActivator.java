/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.servers.wildfly.swarm.core.internal;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CoreActivator implements BundleActivator {

	public static final String ROOT_PLUGIN_ID = "org.jboss.tools.servers.wildfly.swarm";
	
	public static final String PLUGIN_ID = ROOT_PLUGIN_ID+".core";

	private static CoreActivator instance;
	
	public ClasspathChangeListener classpathChangeListener;
	
	public WildlfySwarmDetectionJob detectionJob;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		detectionJob = new WildlfySwarmDetectionJob();
		classpathChangeListener = new ClasspathChangeListener(detectionJob);
		JavaCore.addElementChangedListener(classpathChangeListener, ElementChangedEvent.POST_CHANGE);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		JavaCore.removeElementChangedListener(classpathChangeListener);
		classpathChangeListener = null;
		detectionJob = null;
		instance = null;
	}

	public static CoreActivator getInstance() {
		return instance;
	}
	
}
