/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This first version of the R2RML API was developed jointly at the University of Oslo, 
 * the University of Bolzano, La Sapienza University of Rome, and fluid Operations AG, 
 * as part of the Optique project, www.optique-project.eu
 ******************************************************************************/
package jenaTest;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.Join;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.impl.jena.JenaR2RMLMappingManagerFactory;
import eu.optique.api.mapping.RefObjectMap;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class NTriplesSyntax1_Test{
	
	@Test
	public void test() throws Exception{
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test28.ttl");
		
		R2RMLMappingManager mm = new JenaR2RMLMappingManagerFactory().getR2RMLMappingManager();

		Model m = ModelFactory.createDefaultModel();
		m = m.read(fis,"testMapping", "N-TRIPLE");
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			int cont=0;
			while(pomit.hasNext()){
				pomit.next();
				cont++;
			}
			
			
			if(cont==1){ //we are analyzing the TriplesMap2 
				SubjectMap s=current.getSubjectMap();
				Iterator<GraphMap> gmit=s.getGraphMaps().iterator();
				
				int conta=0;
				while(gmit.hasNext()){
					GraphMap g=gmit.next();
					Assert.assertTrue(g.getConstant().contains("http://example.com/graph/sports"));
					conta++;
				}
				Assert.assertTrue(conta==1);    
			}
			
			
			else{ //we are analyzing the TriplesMap1
				Assert.assertTrue(cont==2); 
				
				pomit=current.getPredicateObjectMaps().iterator();
				
				while(pomit.hasNext()){
					PredicateObjectMap pom=pomit.next();

					Iterator<GraphMap> gmit=pom.getGraphMaps().iterator();
					
					int contt=0;
					while(gmit.hasNext()){
						GraphMap g=gmit.next();
						boolean result=g.getConstant().contains("http://example.com/graph/practise");
						boolean result1=g.getConstant().contains("http://example.com/graph/students");
						
						Assert.assertTrue(result || result1);
						contt++;
					}
					
					Assert.assertTrue(contt==1);  
					
					
					Iterator<RefObjectMap> romit=pom.getRefObjectMaps().iterator();
					while(romit.hasNext()){
						RefObjectMap rom=romit.next();
						
						Assert.assertTrue(rom.getParentMap()!=null);
						
						Iterator<Join> itjoin=rom.getJoinConditions().iterator();
						
						int joincont=0;
						while(itjoin.hasNext()){
							itjoin.next();
							joincont++;
						}	
						Assert.assertTrue(joincont==1); 
						
					}
				}
			}
		}			
			
	}
	
	
}
