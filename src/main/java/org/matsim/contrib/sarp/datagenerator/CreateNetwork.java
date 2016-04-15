package org.matsim.contrib.sarp.datagenerator;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.api.experimental.network.NetworkWriter;
import org.matsim.core.config.Config;
import org.matsim.core.network.algorithms.NetworkCleaner;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordImpl;
import org.matsim.core.utils.geometry.CoordUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;

import com.sun.jndi.url.dns.dnsURLContext;

import org.matsim.core.config.ConfigUtils;


public class CreateNetwork {

   public static void main(String[] args) {
      String osm = "./input/merged-network.osm";
      Config config = ConfigUtils.createConfig();
      Scenario sc = ScenarioUtils.createScenario(config);
      Network net = sc.getNetwork();
      
      CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(
    		  TransformationFactory.WGS84, TransformationFactory.VN_2000);
      
      CoordinateTransformation ct_invert = TransformationFactory.getCoordinateTransformation(
    		  TransformationFactory.VN_2000, TransformationFactory.WGS84);
      CoordImpl coord1 = new CoordImpl(105.817277, 21.015546);
      CoordImpl coord2 = new CoordImpl(105.793319, 21.019634);
      
      double d = CoordUtils.calcDistance(coord1, coord2);
      
      Coord coord = ct.transform(new CoordImpl(21.015546, 105.817277));
      System.out.println(coord);
      
      coord = ct_invert.transform(coord);
      System.out.println(coord);

      
      OsmNetworkReader onr = new OsmNetworkReader(net,ct);
      onr.parse(osm); 
      new NetworkCleaner().run(net);
      new NetworkWriter(net).write("./input/hanoi.xml");
   }
}


