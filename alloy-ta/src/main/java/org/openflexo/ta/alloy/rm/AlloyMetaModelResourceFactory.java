/*
 * (c) Copyright 2013 Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.ta.alloy.rm;

import org.openflexo.foundation.resource.FlexoIODelegate;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.TechnologySpecificFlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.metamodel.AlloyMetaModel;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implementation of ResourceFactory for {@link AlloyMetaModelResource}
 * 
 * @author chamomile93
 *
 */
public class AlloyMetaModelResourceFactory
		extends TechnologySpecificFlexoResourceFactory<AlloyMetaModelResource,
        AlloyMetaModel, AlloyTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(AlloyMetaModelResourceFactory.class.getPackage().getName());

	public static String ECORE_FILE_EXTENSION = ".ecore";

	public static final String URI_KEY = "URI";
	public static final String EXTENSION_KEY = "EXTENSION";
	public static final String PACKAGE_KEY = "PACKAGE";
	public static final String RESOURCE_FACTORY_KEY = "RESOURCE_FACTORY";

	public static String PROPERTY_TYPE = "TYPE";
	public static String TYPE_METAMODEL = "standard";
	public static String TYPE_PROFILE = "profile";
	public static String TYPE_XTEXT = "xtext";
	public static String PROPERTY_XTEXT_STANDALONE_SETUP = "XTEXT_STANDALONE_SETUP";

	public AlloyMetaModelResourceFactory() throws ModelDefinitionException {
		super(AlloyMetaModelResource.class);
	}

	@Override
	public AlloyMetaModel makeEmptyResourceData(AlloyMetaModelResource resource) {
		// TODO
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return true;
	}

	@Override
	public <I> AlloyMetaModelResource registerResource(AlloyMetaModelResource resource, FlexoResourceCenter<I> resourceCenter) {
		super.registerResource(resource, resourceCenter);

		logger.info("register resource uri= " + resource.getURI() +
			 " in rC= " + resourceCenter);
		logger.info("serviceManager= " + resource.getServiceManager());

		TechnologyContextManager<AlloyTechnologyAdapter> technologyContextManager = getTechnologyContextManager(resource.getServiceManager());

		AlloyTechnologyAdapter technologyAdapter = getTechnologyAdapter(resource.getServiceManager());
		technologyAdapter.newMetaModelWasRegistered(resource, resourceCenter);

		// Register the resource in the EMFMetaModelRepository of supplied resource center
		if (resourceCenter != null) {
			registerResourceInResourceRepository(resource,
					technologyContextManager.getTechnologyAdapter().getAlloyMetaModelRepository(resourceCenter));
		}

		return resource;
	}

	@Override
	protected <I> AlloyMetaModelResource initResourceForRetrieving(I serializationArtefact, FlexoResourceCenter<I> resourceCenter)
			throws ModelDefinitionException, IOException {
		{
			logger.warning("Unexpected artefact: " + serializationArtefact);
			return null;
		}
	}

	@Override
	protected <I> FlexoIODelegate<I> makeFlexoIODelegate(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		logger.warning("Unexpected artefact: " + serializationArtefact);
		return null;
	}

}
