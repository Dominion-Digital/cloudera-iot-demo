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
package com.redhat.iot.proxy.rest;

import com.redhat.iot.proxy.model.Line;
import com.redhat.iot.proxy.model.Machine;
import com.redhat.iot.proxy.model.Point;
import com.redhat.iot.proxy.service.DGService;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.ws.rs.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Random;

/**
 * A simple REST service which proxies requests to a local datagrid.
 */

@Path("/machines")
@Singleton
public class MachinesEndpoint {

    @Inject
    DGService dgService;

    @Resource(name = "java:jboss/datasources/impala-ds", lookup = "java:jboss/datasources/impala-ds")
    DataSource dataSource;

    @GET
    @Path("/history/query")
    @Produces({"application/json"})
    public List<Point> getHistory(@QueryParam("topic") String topic, @QueryParam("metric") String metric) throws SQLException {
		
		System.out.println("getHistory(" + topic + ", " + metric + ")");
		
		switch (metric) {
			default: return getPoints(50, 12.5, 14.6);
		}
		
		/*
		Connection con = null;
        try {

            con = dataSource.getConnection();

            Statement stmt = con.createStatement();

//        String query = "SELECT millis,avg(value) over " +
//                "(partition by metric,motor_id order by millis rows between 150 preceding and 150 following) " +
//                "as moving_average from iiot.telemetry where metric='"+metric+"' and motor_id REGEXP '" +
//                topic + "' and millis >= ((unix_timestamp() * 1000) - (24 * 60 * 60 * 1000)) order by millis limit 500";

            String query = "SELECT \n" +
                    "    (bucketed_values.bucket * 300000) AS bucket_millis,\n" +
                    "    avg(bucketed_values.value) AS bucket_average_value\n" +
                    "FROM (\n" +
                    "    SELECT value, ceil(millis/300000) AS bucket\n" +
                    "    FROM iiot.telemetry\n" +
                    "    WHERE\n" +
                    "        metric='" + metric + "' AND\n" +
                    "        motor_id REGEXP '" + topic + "' AND \n" +
                    "        millis >= ((unix_timestamp() * 1000) - (24 * 60 * 60 * 1000))\n" +
                    "    ) AS bucketed_values\n" +
                    "GROUP BY bucketed_values.bucket\n" +
                    "ORDER BY bucket_millis;\n" +
                    "\n";

            long start = System.currentTimeMillis();

            ResultSet rs = stmt.executeQuery(query);

            long end1 = System.currentTimeMillis();

            ResultSetMetaData rsmd = rs.getMetaData();

            int count = 0;

            while (rs.next()) {
                count++;
                // the example query returns one String column
                temp.add(new Point(rs.getLong(1), rs.getDouble(2)));
            }
            long end2 = System.currentTimeMillis();

            System.out.println(count + " results took " + (end1 - start) + "ms, then another " + (end2 - end1) + "ms to serialize");
        } finally {
            if (con != null) {
                con.close();
            }
        }
		*/
    }
	
	private List<Point> getPoints(int numPoints, double minValue, double maxValue) {
		List<Point> temp = new ArrayList<>();
        for(int i=1; i<numPoints; i++){
			// La primera columna es el timestamp y la segunda el punto que se quiere pintar
			temp.add(new Point(System.currentTimeMillis() / 1000L, randDouble(minValue,maxValue)));
		}
		return temp;
    }
	
	public static double randDouble(int min, int max) {
		final Random r = new Random();
		final long seed = r.nextLong();
		r.setSeed(seed);
		//int randomNum = r.nextInt((max - min) + 1) + min;
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomNum;
	}
}
