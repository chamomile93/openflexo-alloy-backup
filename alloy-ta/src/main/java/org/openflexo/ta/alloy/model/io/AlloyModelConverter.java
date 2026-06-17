/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012, THALES SYSTEMES AEROPORTES - All Rights Reserved
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Emfconnector, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.ta.alloy.model.io;

import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;
import org.openflexo.ta.alloy.model.AlloyModel;
import org.openflexo.ta.alloy.model.AlloyObjectIndividual;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Alloy Model Converter.
 * 
 * @author chamomile93
 */
public class AlloyModelConverter {

	private static final Logger logger = Logger.getLogger(AlloyModelConverter.class.getPackage().getName());

	/** Builder. */
	protected AlloyModelBuilder builder = new AlloyModelBuilder();
	/** Concepts. */
	protected final Map<Object, AlloyObjectIndividual> individuals =
			new HashMap<>();

	/**
	 * Constructor.
	 */
	public AlloyModelConverter() {
	}

	/**
	 * Convert a Resource into a Model with MetaModel support.
	 *
	 * @param aResource
	 * @return
	 * @throws IOException
	 */
	public AlloyModel convertModel(Object aResource) {

		AlloyModel model = builder.buildModel(this, aResource);
		return model;
	}

	/**
	 * Convert a Resource into a Model with MetaModel support.
	 * 
	 * @param metaModel
	 * @param aResource
	 * @return
	 * @throws IOException
	 */
	public AlloyModel convertModel(AlloyMetaModel metaModel,
                                   Object aResource) {

		AlloyModel model = builder.buildModel(metaModel, this, aResource);
		return model;
	}

	/**
	 * Getter of individuals.
	 *
	 * @return the individuals value
	 */
	public Map<Object, AlloyObjectIndividual> getIndividuals() {
		return individuals;
	}
}
