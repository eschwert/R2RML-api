/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 * 
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
package eu.optique.api.mapping;

/**
 * R2RML Logical Table
 * 
 * @author Marius Strandhaug
 */
public interface LogicalTable extends ManageResource, SerializeR2RML {

	/**
	 * Returns the effective SQL query of this LogicalTable. The effective SQL
	 * query of a R2RMLView is it's own SQL query. For a SQLTable it's
	 * "SELECT * FROM {table}".
	 * 
	 * @return The effective SQL query of this LogicalTable.
	 */
	public String getSQLQuery();

}
