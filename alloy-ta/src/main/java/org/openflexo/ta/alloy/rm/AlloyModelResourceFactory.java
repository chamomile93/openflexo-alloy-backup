package org.openflexo.ta.alloy.rm;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceFactory;
import org.openflexo.foundation.resource.TechnologySpecificFlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.pamela.exceptions.ModelDefinitionException;
import org.openflexo.ta.alloy.AlloyTechnologyAdapter;
import org.openflexo.ta.alloy.AlloyTechnologyContextManager;
import org.openflexo.ta.alloy.model.AlloyModel;
import org.openflexo.ta.alloy.model.io.AlloyModelConverter;

import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * Supposed to be an implementation of {@link FlexoResourceFactory} for
 * {@link AlloyModelResource}
 *
 * @author chamomile93
 *
 */
public class AlloyModelResourceFactory extends TechnologySpecificFlexoResourceFactory<AlloyModelResource, AlloyModel, AlloyTechnologyAdapter> {

    static final Logger logger = Logger.getLogger(AlloyModelResourceFactory.class.getPackage().getName());

    // TODO look for this duplicate constant in the code
    public static String ALS_FILE_EXTENSION = ".als";

    public AlloyModelResourceFactory() throws ModelDefinitionException {
        super(AlloyModelResource.class);
    }

    @Override
    public AlloyModel makeEmptyResourceData(AlloyModelResource resource) {
        AlloyModelConverter converter = new AlloyModelConverter();
        return converter.convertModel(resource.getMetaModelResource().getMetaModelData(), resource.getAlloyResource());
    }

    @Override
    public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
        return (resourceCenter.retrieveName(serializationArtefact).endsWith(ALS_FILE_EXTENSION)) && !(resourceCenter.retrieveName(serializationArtefact).startsWith("~"));
    }

    // TODO probably use {@link org.alloytools} source code to check if this
    // is a valid alloy tools rather than redoing things here with faulty reasoning
//	@Override
//	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
//		logger.log(Level.INFO, "serializationArtefact= " + serializationArtefact.toString());
//		logger.log(Level.INFO, "rC= " + resourceCenter.getName());
//		for (I content : resourceCenter.getContents(serializationArtefact)) {
//			// TODO this log doesn't seem helpful
//			logger.log(Level.INFO, "contentSerialization= " + content.toString());
//		}
//		return false;
//	}

    @Override
    public <I> AlloyModelResource registerResource(AlloyModelResource resource, FlexoResourceCenter<I> resourceCenter) {
        super.registerResource(resource, resourceCenter);

        TechnologyContextManager<AlloyTechnologyAdapter> technologyContextManager = getTechnologyContextManager(resource.getServiceManager());
        ((AlloyTechnologyContextManager) technologyContextManager).registerModel(resource);

        registerResourceInResourceRepository(resource, getTechnologyAdapter(resourceCenter.getServiceManager()).getAlloyModelRepository(resourceCenter));

        // Register the resource in the repository of supplied resource center
        // TODO might be incorrect , globalMight might be innaccessible, and the
        // specific type given is mismatch
//		registerResourceInResourceRepository(resource,
//				getTechnologyAdapter(resourceCenter.getServiceManager()).getAlloyCompilationUnitResourceRepository(resourceCenter));
        registerResourceInResourceRepository(resource, getTechnologyAdapter(resourceCenter.getServiceManager()).getAlloyResourceRepository(resourceCenter));
        registerResourceInResourceRepository(resource, getTechnologyAdapter(resourceCenter.getServiceManager()).getGlobalRepository(resourceCenter));

        return resource;
    }


    @Override
    public <I> AlloyModelResource retrieveResource(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) throws ModelDefinitionException, IOException {

        logger.info("retrieveResource serializationArtefact= " + serializationArtefact);
        if (getRegisteredResource(serializationArtefact) != null) {
            return getRegisteredResource(serializationArtefact);
        }

        TechnologyContextManager<AlloyTechnologyAdapter> technologyContextManager = getTechnologyContextManager(resourceCenter.getServiceManager());

        if (isAlloyArtefact(serializationArtefact, resourceCenter)) {
            AlloyModelResource returned = initResourceForRetrieving(serializationArtefact, resourceCenter);
            return registerResource(returned, resourceCenter);
        }
        return null;
    }

    @Override
    protected <I> AlloyModelResource initResourceForRetrieving(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) throws ModelDefinitionException, IOException {
        AlloyModelResource returned = super.initResourceForRetrieving(serializationArtefact, resourceCenter);

        return returned;
    }

    private <I> boolean isAlloyArtefact(I content, FlexoResourceCenter<I> resourceCenter) {
        // TODO idf why need two concepts for this;
        // maybe refactor this constant into one
        return resourceCenter.retrieveName(content).endsWith(AlloyModelResourceFactory.ALS_FILE_EXTENSION);
    }
}