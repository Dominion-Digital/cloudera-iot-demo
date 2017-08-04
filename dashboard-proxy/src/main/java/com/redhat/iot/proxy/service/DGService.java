/*
 * ******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc. and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *
 * ******************************************************************************
 */
package com.redhat.iot.proxy.service;

import com.redhat.iot.proxy.model.*;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Properties;

@ApplicationScoped
public class DGService {

    private RemoteCacheManager cacheManager;


    public Map<String, Customer> getCustomers() {
        return cacheManager.getCache("customer");
    }
    public Map<String, Facility> getFacilities() {
        return cacheManager.getCache("facility");
    }
    public Map<String, Line> getProductionLines() {
        return cacheManager.getCache("lines");
    }
    public Map<String, Machine> getMachines() {
        return cacheManager.getCache("machines");
    }
    public Map<String, Run> getRuns() {
        return cacheManager.getCache("runs");
    }

    public DGService() {


        String host = System.getenv("DATASTORE_HOST");
        if (host == null) {
            host = "localhost";
        }

        int port = 11333;
        String portStr = System.getenv("DATASTORE_PORT");
        if (portStr != null) {
            port = Integer.parseInt(portStr);
        }

        String cacheNames = System.getenv("DATASTORE_CACHE");
        if (cacheNames == null) {
            cacheNames = "customer,facility,lines,machines,runs";
        }

        System.out.println("DG Proxy initializing to " + host + ":" + port + " cache:" + cacheNames);

        Properties props = new Properties();
        props.setProperty("infinispan.client.hotrod.protocol_version", "1.0");

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.withProperties(props).addServer()
                .host(host)
                .port(port);
        cacheManager = new RemoteCacheManager(builder.build());

        System.out.println("DG Proxy connected to " + host + ":" + port + " preconfigured caches: " + cacheNames);

    }

}
